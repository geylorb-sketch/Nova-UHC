package net.novauhc.dandadan.roles.joseph;

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

public class JosephRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOSEPH_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability hamonOverdrive = new HamonOverdriveAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOSEPH_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability rebuff = new RebuffAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOSEPH_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability clacker = new ClackerAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOSEPH_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability ripple = new RippleHairAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOSEPH_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability run = new NigerundayoCommand();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOSEPH_PASSIVE_THOMSON_NAME", type = VariableType.ABILITY)
    private Ability thomsonPassive = new ThomsonPassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOSEPH_PASSIVE_PREDICTION_NAME", type = VariableType.ABILITY)
    private Ability predictionPassive = new PredictionPassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOSEPH_PASSIVE_HERMIT_PURPLE_NAME", type = VariableType.ABILITY)
    private Ability hermitPurple = new HermitPurpleAbility();

public JosephRole() {
        setCamp(DanDaDanCamps.SOLO);
    }

    @Override public int getId()                { return 25; }
    @Override public String getName()           { return "Joseph"; }
    @Override public Material getIconMaterial() { return Material.LEASH; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLangExt3.JOSEPH_DESC, player);
    }

    // Passif Prédiction

    // Passif Thomson (3❤ sous 2❤ → 3 flèches)

    // Hermit Purple (accrochage)

    // Hamon Overdrive

    // Rebuff Overdrive

    // Clacker Boomerang

    // Ripple Hair Attack

    // NIGERUNDAYO!
}
