package net.novauhc.dandadan.roles.csg;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanCamps;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CompteSaintGermainRole extends DanDaDanRole {

    public enum Fiole { NONE, JUMP_CRONE, COUPE_CHEVEUX, TIGRE_EAU }
    private Fiole currentFiole = Fiole.JUMP_CRONE;

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CSG_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability couteau = new CouteauCommand();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CSG_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability fiole = new FioleAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "CSG_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability carte = new CarteAbility();

    public CompteSaintGermainRole() {
        setCamp(DanDaDanCamps.SOLO);
        // Épée diamant Sharp IV donnée à onGive
    }

    @Override public int getId()                { return 23; }
    @Override public String getName()           { return "Compte de Saint-Germain"; }
    @Override public Material getIconMaterial() { return Material.DIAMOND_SWORD; }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer(); if (player == null) return;
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        sword.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 4);
        player.getInventory().addItem(sword);
        super.onGive(uhcPlayer);
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLangExt3.CSG_DESC, player);
    }

    public Fiole getCurrentFiole() { return currentFiole; }
    public void nextFiole() {
        currentFiole = switch (currentFiole) {
            case JUMP_CRONE  -> Fiole.COUPE_CHEVEUX;
            case COUPE_CHEVEUX -> Fiole.TIGRE_EAU;
            default -> Fiole.JUMP_CRONE;
        };
    }

    // /ddd couteau

    // Fiole (clic-droit cycle, clic-gauche utilise)

    // Carte aléatoire
}

// ════════════════════════════════════════════
//  Caesar (id 24) — duo avec Joseph
// ════════════════════════════════════════════
