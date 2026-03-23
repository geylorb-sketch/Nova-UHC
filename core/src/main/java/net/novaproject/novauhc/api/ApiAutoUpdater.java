package net.novaproject.novauhc.api;

import com.google.gson.JsonObject;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class ApiAutoUpdater {

    private final ApiManager api;
    private final Supplier<JsonObject> configSupplier;
    private final Logger log;

    public ApiAutoUpdater(ApiManager api, Supplier<JsonObject> configSupplier) {
        this.api = api;
        this.configSupplier = configSupplier;
        this.log = api.log;
    }

    public void start(ScheduledExecutorService scheduler) {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                JsonObject config = configSupplier.get();
                if (config != null) {
                    api.pushLiveConfig(config);
                }
            } catch (Exception e) {
                log.warning("⚠️ AutoUpdater: failed to push live config: " + e.getMessage());
            }
        }, 30, 60, TimeUnit.SECONDS);
        log.info("✅ ApiAutoUpdater démarré (push toutes les 60s)");
    }
}
