package net.novaproject.novauhc.database;

import com.google.gson.*;
import net.novaproject.novauhc.Main;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class ApiManager {

    private final Plugin plugin;
    private final String apiUrl;
    private final String apiKey;
    public final Logger log;
    private final Gson gson;
    private final ScheduledExecutorService scheduler;

    private String jwt;
    private String userUuid;
    private String serverUuid;
    private String currentGameUuid;

    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 10000;
    private static final int MAX_RETRIES = 3;

    public static ApiManager get() { return Main.getApiManager(); }

    public ApiManager(Plugin plugin, String apiUrl, String apiKey) {
        this.plugin = plugin;
        this.apiUrl = apiUrl.endsWith("/") ? apiUrl.substring(0, apiUrl.length() - 1) : apiUrl;
        this.apiKey = apiKey;
        this.log = plugin.getLogger();
        this.gson = new GsonBuilder().create();
        this.scheduler = Executors.newScheduledThreadPool(2);

        try {
            authenticate();
            startHeartbeat();
            log.info("✅ API Manager initialized");
        } catch (IOException e) {
            log.severe("❌ API authentication failed: " + e.getMessage());
        }
    }

    

    private void authenticate() throws IOException {
        JsonObject body = new JsonObject();
        body.addProperty("serverName", plugin.getConfig().getString("api.server-name", "UHC Server"));
        body.addProperty("ip", getServerIP());
        body.addProperty("port", getServerPort());
        body.addProperty("minecraftVersion", "1.8.8");
        body.addProperty("pluginVersion", plugin.getDescription().getVersion());

        JsonObject response = postWithApiKey("/auth/login", body);
        JsonObject data = response.getAsJsonObject("data");

        this.jwt = data.get("token").getAsString();
        this.userUuid = data.getAsJsonObject("user").get("uuid").getAsString();
        this.serverUuid = data.getAsJsonObject("server").get("uuid").getAsString();

        log.info("Server registered: " + data.getAsJsonObject("server").get("name").getAsString());
    }

    private void startHeartbeat() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                JsonObject body = new JsonObject();
                body.addProperty("status", currentGameUuid != null ? "ingame" : "online");
                body.addProperty("playersOnline", plugin.getServer().getOnlinePlayers().size());

                if (currentGameUuid != null) {
                    JsonObject game = new JsonObject();
                    game.addProperty("uuid", currentGameUuid);
                    body.add("currentGame", game);
                }
                callAsync("POST", "/server/heartbeat", body);
            } catch (Exception ignored) {}
        }, 30, 30, TimeUnit.SECONDS);
    }

    public void shutdown() {
        try {
            post("/server/shutdown", null);
            scheduler.shutdown();
            scheduler.awaitTermination(3, TimeUnit.SECONDS);
            log.info("Server shut down successfully");
        } catch (Exception ignored) {}
    }

    






    public CompletableFuture<String> gameStart(
            String mode,
            String scenario,
            List<String> scenarios,
            JsonObject scenarioConfig,
            int border,
            List<PlayerInfo> players
    ) {
        JsonObject body = new JsonObject();
        body.addProperty("mode", mode);

        if (scenario != null && !scenario.isEmpty()) {
            body.addProperty("scenario", scenario);
        }

        body.add("scenarios", gson.toJsonTree(scenarios));
        body.add("scenarioConfig", scenarioConfig);
        body.addProperty("border", border);

        JsonArray playerArray = new JsonArray();
        for (PlayerInfo p : players) {
            JsonObject playerJson = new JsonObject();
            playerJson.addProperty("uuid", p.uuid());
            playerJson.addProperty("name", p.name());
            playerArray.add(playerJson);
        }
        body.add("players", playerArray);

        log.info("Starting " + mode + " game" + (scenario != null ? " (" + scenario + ")" : ""));

        return callAsync("POST", "/game/start", body)
                .thenApply(response -> {
                    JsonObject data = response.getAsJsonObject("data");
                    currentGameUuid = data.get("gameUuid").getAsString();
                    log.info("Game started with UUID: " + currentGameUuid);
                    return currentGameUuid;
                });
    }


    public CompletableFuture<JsonObject> gameEnd(
            String mode,
            String scenario,
            String winCondition,
            List<WinnerInfo> winners,
            List<PlayerStats> playerStats,
            int duration,
            Map<String, Object> specialData
    ) {
        JsonObject body = new JsonObject();
        body.addProperty("gameUuid", currentGameUuid);
        body.addProperty("mode", mode);
        body.addProperty("winCondition", winCondition);

        if (scenario != null && !scenario.isEmpty()) {
            body.addProperty("scenario", scenario);
        }

        body.add("winners", gson.toJsonTree(winners));
        body.add("players", gson.toJsonTree(playerStats));
        body.addProperty("duration", duration);

        if (specialData != null && !specialData.isEmpty()) {
            JsonObject special = gson.toJsonTree(specialData).getAsJsonObject();
            body.add("specialData", special);
        }

        log.info("Ending " + mode + " game with " + winners.size() + " winner(s)" +
                (scenario != null ? " (scenario: " + scenario + ")" : ""));

        return callAsync("POST", "/game/end", body)
                .thenApply(r -> {
                    currentGameUuid = null;
                    return r;
                });
    }



    public CompletableFuture<JsonObject> gameEndSolo(
            WinnerInfo winner,
            List<PlayerStats> playerStats,
            int duration
    ) {
        return gameEnd("Solo", null, "solo", List.of(winner), playerStats, duration, null);
    }

    public CompletableFuture<JsonObject> gameEndTeam(
            int teamSize,
            List<WinnerInfo> teamWinners,
            List<PlayerStats> playerStats,
            int duration
    ) {
        return gameEnd("Team " + teamSize, null, "team", teamWinners, playerStats, duration, null);
    }

    public CompletableFuture<JsonObject> gameEndSpecial(
            String scenarioName,
            String winCondition,
            List<WinnerInfo> winners,
            List<PlayerStats> playerStats,
            int duration,
            Map<String, Object> specialData
    ) {
        return gameEnd("Special", scenarioName, winCondition, winners, playerStats, duration, specialData);
    }

    

    public CompletableFuture<JsonObject> addKills(String uuid, int n) { return addStat(uuid, "kill", n); }
    public CompletableFuture<JsonObject> addWins(String uuid, int n) { return addStat(uuid, "win", n); }
    public CompletableFuture<JsonObject> addLose(String uuid, int n) { return addStat(uuid, "lose", n); }
    public CompletableFuture<JsonObject> addDeath(String uuid, int n) { return addStat(uuid, "death", n); }
    public CompletableFuture<JsonObject> addCoins(String uuid, int n) { return addStat(uuid, "coins", n); }

    private CompletableFuture<JsonObject> addStat(String uuid, String field, int amount) {
        JsonObject body = new JsonObject();
        body.addProperty("uuid", uuid);
        body.addProperty("field", field);
        body.addProperty("amount", amount);
        return callAsync("POST", "/player/stat/add", body);
    }



    

    public CompletableFuture<JsonObject> saveConfig(String name, JsonObject config) {
        JsonObject body = new JsonObject();
        body.addProperty("name", name);
        body.add("config", config);
        return callAsync("POST", "/config/save", body);
    }

    public CompletableFuture<JsonObject> listConfigs() {
        return callAsync("GET", "/config/list", null);
    }

    public CompletableFuture<JsonObject> getConfig(String name) {
        return callAsync("GET", "/config/" + encode(name), null);
    }

    public CompletableFuture<JsonObject> deleteConfig(String name) {
        return callAsync("DELETE", "/config/" + encode(name), null);
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }


    

    private JsonObject post(String endpoint, JsonObject body) throws IOException {
        return request("POST", endpoint, body, false);
    }

    private JsonObject postWithApiKey(String endpoint, JsonObject body) throws IOException {
        return request("POST", endpoint, body, true);
    }

    public CompletableFuture<JsonObject> callAsync(String method, String endpoint, JsonObject body) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return request(method, endpoint, body, false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, scheduler);
    }

    private JsonObject request(String method, String endpoint,
                               JsonObject body, boolean useApiKey) throws IOException {

        int retries = 0;
        while (retries < MAX_RETRIES) {
            try {
                return executeRequest(method, endpoint, body, useApiKey);
            } catch (IOException e) {
                if (++retries >= MAX_RETRIES) throw e;
                try { Thread.sleep((long) Math.pow(2, retries) * 1000); }
                catch (InterruptedException ignored) {}
            }
        }
        throw new IOException("Failed after retries");
    }

    private JsonObject executeRequest(String method, String endpoint,
                                      JsonObject body, boolean useApiKey) throws IOException {

        HttpURLConnection conn =
                (HttpURLConnection) new URL(buildEndpoint(endpoint)).openConnection();

        conn.setRequestMethod(method);
        conn.setConnectTimeout(CONNECT_TIMEOUT);
        conn.setReadTimeout(READ_TIMEOUT);
        conn.setRequestProperty("Content-Type", "application/json");

        if (useApiKey)
            conn.setRequestProperty("X-API-Key", apiKey);
        else
            conn.setRequestProperty("Authorization", "Bearer " + jwt);

        if (body != null &&
                (method.equals("POST") || method.equals("PUT") || method.equals("PATCH"))) {

            conn.setDoOutput(true);
            try (OutputStream os = conn.getOutputStream()) {
                os.write(gson.toJson(body).getBytes(StandardCharsets.UTF_8));
            }
        }

        int code = conn.getResponseCode();
        if (code < 200 || code >= 300)
            throw new IOException("HTTP " + code);

        try (BufferedReader br =
                     new BufferedReader(new InputStreamReader(conn.getInputStream()))) {

            return gson.fromJson(br, JsonObject.class);
        }
    }

    

    private String buildEndpoint(String path) {
        if (!path.startsWith("/")) path = "/" + path;
        return apiUrl + path;
    }

    private String getServerIP() {
        return plugin.getServer().getIp().isEmpty()
                ? "unknown"
                : plugin.getServer().getIp();
    }

    private int getServerPort() {
        return plugin.getServer().getPort();
    }

    

    public record PlayerInfo(String uuid, String name) {}
    public record PlayerStats(String uuid, String name, int kills, int deaths, int placement, String camp) {}
    public record WinnerInfo(String type, String uuid, String name, int kills, String camp) {}
}