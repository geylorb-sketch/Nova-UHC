package net.novauhc.dandadan;

import net.novaproject.novauhc.ability.template.CommandAbility;
import net.novaproject.novauhc.command.Command;
import net.novaproject.novauhc.command.CommandArguments;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.Role;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.utils.YokaiRegistry;
import net.novauhc.dandadan.world.YokaiZoneManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DanDaDanCMD extends Command {

    @Override
    public void execute(CommandArguments args) {
        if (!(args.getSender() instanceof Player player)) {
            args.getSender().sendMessage(LangManager.get().get(DanDaDanLang.CMD_PLAYERS_ONLY));
            return;
        }

        String[] arguments = args.getArguments();
        if (arguments.length == 0) { sendHelp(player); return; }
        UHCPlayer uhcPlayer = UHCPlayerManager.get().getPlayer(player);
        switch (arguments[0].toLowerCase()) {
            case "accept" -> handleAccept(player);
            case "yokai" -> handleYokaiList(player);

            default -> {
                if (uhcPlayer != null) {
                    Role role = DanDaDan.get().getRoleByUHCPlayer(uhcPlayer);
                    if (role == null) {
                        player.sendMessage(LangManager.get().get(DanDaDanLang.CMD_NO_ROLE, player));
                        return;
                    }
                    role.getAbilities().stream()
                            .filter(a -> a instanceof CommandAbility)
                            .map(a -> (CommandAbility) a)
                            .filter(ability -> ability.getCommandKey().equalsIgnoreCase(arguments[0]))
                            .findFirst()
                            .ifPresent(ability -> ability.tryCommandUse(player, java.util.Arrays.copyOfRange(arguments, 1, arguments.length)));
                } else {
                    LangManager.get().send(DanDaDanLang.CMD_UNKNOWN, player);
                }
            }
        }
    }

    private void handleAccept(Player player) {
        if (!YokaiZoneManager.get().isInTrial(player)) {
            LangManager.get().send(DanDaDanLang.CMD_NOT_IN_TRIAL, player);
            return;
        }
        YokaiZoneManager.get().acceptPact(player);
    }

    private void handleYokaiList(Player player) {
        player.sendMessage(LangManager.get().get(DanDaDanLang.CMD_YOKAI_HEADER));
        for (YokaiRegistry yokai : YokaiRegistry.values()) {
            String status;
            if (!yokai.isEnabled()) {
                status = LangManager.get().get(DanDaDanLang.CMD_YOKAI_DISABLED);
            } else if (DanDaDan.get().isRoleClaimed(yokai.getRoleClass())) {
                status = LangManager.get().get(DanDaDanLang.CMD_YOKAI_CLAIMED);
            } else {
                status = LangManager.get().get(DanDaDanLang.CMD_YOKAI_ENABLED);
            }
            String type = yokai.getType() == YokaiRegistry.YokaiType.SPECIAL ? "§6★" : "§7•";
            player.sendMessage(LangManager.get().get(DanDaDanLang.CMD_YOKAI_LINE).replace("%type%", type).replace("%name%", yokai.name()).replace("%status%", status));
        }
    }



    private void sendHelp(Player player) {
        player.sendMessage(LangManager.get().get(DanDaDanLang.CMD_HELP_HEADER, player));
        player.sendMessage(LangManager.get().get(DanDaDanLang.CMD_HELP_ACCEPT, player));
        player.sendMessage(LangManager.get().get(DanDaDanLang.CMD_HELP_YOKAI, player));
    }

    @Override
    public List<String> tabComplete(CommandArguments args) {
        String[] a = args.getArguments();

        if (a.length == 1) {
            List<String> subs = new ArrayList<>(List.of("accept", "yokai"));
            if (args.getSender().hasPermission("novauhc.host")) {
                subs.add("config");
                subs.add("toggle");
                subs.add("reload");
            }

            if (args.getSender() instanceof Player player) {
                UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
                if (uhc != null) {
                    Role role = DanDaDan.get().getRoleByUHCPlayer(uhc);
                    if(role == null) return List.of();
                    role.getAbilities().stream()
                            .filter(a2 -> a2 instanceof CommandAbility)
                            .map(a2 -> ((CommandAbility) a2).getCommandKey())
                            .forEach(subs::add);
                }
            }

            return getStrings(args, subs.toArray(new String[0]));
        }


        return List.of();
    }
}
