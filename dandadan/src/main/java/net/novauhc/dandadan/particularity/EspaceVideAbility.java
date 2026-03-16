package net.novauhc.dandadan.particularity;

import net.novaproject.novauhc.ability.CommandAbility;
import org.bukkit.entity.Player;

/**
 * EspaceVideAbility — CommandAbility partagée (/ddd vide).
 * À ajouter dans le constructeur des rôles concernés via getAbilities().add(new EspaceVideAbility()).
 * Rôles : Reze, Bamora, Œil Maléfique, Monstre de Flatwoods, Reiko Kashima, Nessie.
 */
public class EspaceVideAbility extends CommandAbility {

    public EspaceVideAbility() {
        setCooldown(600); // 10 minutes
    }

    @Override
    public String getName()       { return "Espace Vide"; }

    @Override
    public String getCommandKey() { return "vide"; }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length >= 2) {
            // /ddd vide config <lava|fleches|rayon|max> <valeur>
            handleConfig(player, args);
            return false; // pas de consommation d'usage
        }
        // Activation
        boolean ok = EspaceVideManager.get().activate(player);
        if (ok) setCooldown(600);
        return ok;
    }

    private void handleConfig(Player player, String[] args) {
        if (args.length < 4) {
            player.sendMessage("§b[Espace Vide] §7Config : /ddd vide config <lava|fleches|rayon|max> <valeur>");
            return;
        }
        String param = args[2].toLowerCase();
        String val   = args[3];
        switch (param) {
            case "lava"    -> {
                EspaceVideManager.get().setAllowLava(Boolean.parseBoolean(val));
                player.sendMessage("§b[Espace Vide] §7Lave : " + val);
            }
            case "fleches" -> {
                EspaceVideManager.get().setMaxArrows(Integer.parseInt(val));
                player.sendMessage("§b[Espace Vide] §7Flèches max : " + val);
            }
            case "rayon"   -> {
                EspaceVideManager.get().setRadius(Integer.parseInt(val));
                player.sendMessage("§b[Espace Vide] §7Rayon : " + val);
            }
            case "max"     -> {
                EspaceVideManager.get().setMaxPlayers(Integer.parseInt(val));
                player.sendMessage("§b[Espace Vide] §7Joueurs max : " + val);
            }
            default -> player.sendMessage("§c✘ Paramètre inconnu : " + param);
        }
    }
}
