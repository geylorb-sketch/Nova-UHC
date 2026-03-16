package net.novauhc.dandadan.roles.caesar;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanCamps;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novauhc.dandadan.roles.joseph.JosephRole;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CaesarRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CAESAR_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability savonLauncher = new SavonLauncherAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CAESAR_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability savonLenses = new SavonLensesAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CAESAR_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability savonCutter = new SavonCutterAbility();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CAESAR_PASSIVE_HAMON_NAME", type = VariableType.ABILITY)
    private Ability hamonPassive = new CaesarHamonPassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CAESAR_PASSIVE_BANDANA_NAME", type = VariableType.ABILITY)
    private Ability bandanaPassive = new BandanaPassive();

public CaesarRole() {
        setCamp(DanDaDanCamps.SOLO);
    }

    @Override public int getId()                { return 24; }
    @Override public String getName()           { return "Caesar"; }
    @Override public Material getIconMaterial() { return Material.SKULL_ITEM; }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        // Informe Caesar du pseudo de Joseph
        if (DanDaDan.get() != null) {
            UHCPlayerManager.get().getPlayingOnlineUHCPlayers().forEach(p -> {
                var role = DanDaDan.get().getRoleByUHCPlayer(p);
                if (role instanceof JosephRole && uhcPlayer.getPlayer() != null) {
                    String msg = LangManager.get().get(DanDaDanLangExt3.CAESAR_JOSEPH_NAME, uhcPlayer.getPlayer())
                            .replace("%name%", p.getPlayer() != null ? p.getPlayer().getName() : "?");
                    uhcPlayer.getPlayer().sendMessage(msg);
                }
            });
        }
        super.onGive(uhcPlayer);
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLangExt3.CAESAR_DESC, player);
    }

    // Passif Hamon : renvoie feu/poison

    // Passif Bandana : 10% résistance, transmissible à Joseph

    // Savon Launcher

    // Savon Lenses

    // Savon Cutter

}

// ════════════════════════════════════════════
//  Joseph (id 25) — duo avec Caesar
// ════════════════════════════════════════════
