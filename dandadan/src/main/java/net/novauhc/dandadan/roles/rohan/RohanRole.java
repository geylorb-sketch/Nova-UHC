package net.novauhc.dandadan.roles.rohan;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanCamps;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RohanRole extends DanDaDanRole {

    private boolean standActive = false;

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROHAN_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability heavensDoor = new HeavensDoorAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROHAN_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability livre = new LivreAbility();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROHAN_PASSIVE_ECRIVAIN_NAME", type = VariableType.ABILITY)
    private Ability ecrivainPassive = new EcrivainPassive();

public RohanRole() {
        setCamp(DanDaDanCamps.SOLO);
    }

    @Override public int getId()                { return 33; }
    @Override public String getName()           { return "Rohan"; }
    @Override public Material getIconMaterial() { return Material.BOOK; }
    @Override public String getDescription(Player player) { return LangManager.get().get(DanDaDanLangExt3.ROHAN_DESC, player); }

    public boolean isStandActive() { return standActive; }
    public void setStandActive(boolean b) { standActive = b; }

    // Passif écrivain (placeholder)

    // Heaven's Door (Stand)

    // Livre : transforme un joueur en livre 5s
}
