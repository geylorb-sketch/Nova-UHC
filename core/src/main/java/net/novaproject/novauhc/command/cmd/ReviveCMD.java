package net.novaproject.novauhc.command.cmd;

import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.ability.template.ReviveAbility;
import net.novaproject.novauhc.command.Command;
import net.novaproject.novauhc.command.CommandArguments;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.scenario.ScenarioManager;
import net.novaproject.novauhc.scenario.role.ScenarioRole;
import net.novaproject.novauhc.scenario.role.Role;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReviveCMD extends Command {

    @Override
    public void execute(CommandArguments args) {
        if (!(args.getSender() instanceof Player sender)) return;
        if (UHCManager.get().getGameState() != UHCManager.GameState.INGAME) {
            LangManager.get().send(CommonLang.ERROR_NOT_STARTED, sender);
            return;
        }
        if (args.getArguments().length < 1) {
            LangManager.get().send(CommonLang.REVIVE_CMD_USAGE, sender);
            return;
        }

        String targetName = args.getArgument(0);
        Player targetPlayer = Bukkit.getPlayer(targetName);
        if (targetPlayer == null) {
            LangManager.get().send(CommonLang.PLAYER_NOT_FOUND, sender, Map.<String, Object>of("%player%", targetName));
            return;
        }

        UHCPlayer target = UHCPlayerManager.get().getPlayer(targetPlayer);
        UHCPlayer reviver = UHCPlayerManager.get().getPlayer(sender);

        // Find active ScenarioRole
        ScenarioRole<?> scenarioRole = ScenarioManager.get().getActiveScenarios().stream()
                .filter(s -> s instanceof ScenarioRole)
                .map(s -> (ScenarioRole<?>) s)
                .findFirst()
                .orElse(null);

        if (scenarioRole == null || scenarioRole.getPendingRevive(target.getUniqueId()) == null) {
            LangManager.get().send(CommonLang.REVIVE_NO_PENDING, sender, Map.<String, Object>of("%player%", targetName));
            return;
        }

        Role role = scenarioRole.getRoleByUHCPlayer(reviver);
        if (role == null) {
            LangManager.get().send(CommonLang.REVIVE_NO_ABILITY, sender);
            return;
        }

        List<ReviveAbility> reviveAbilities = role.getAbilities().stream()
                .filter(a -> a instanceof ReviveAbility)
                .map(a -> (ReviveAbility) a)
                .filter(a -> a.canRevive(reviver, target))
                .sorted(Comparator.comparingInt(ReviveAbility::getPriority))
                .collect(Collectors.toList());

        if (reviveAbilities.isEmpty()) {
            LangManager.get().send(CommonLang.REVIVE_NO_ABILITY, sender);
            return;
        }

        scenarioRole.completeRevive(target, reviver, reviveAbilities.get(0));
    }
}
