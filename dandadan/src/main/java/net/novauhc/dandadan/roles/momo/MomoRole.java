package net.novauhc.dandadan.roles.momo;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;

public class MomoRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MOMO_ABILITY_MOEMOE_NAME", type = VariableType.ABILITY)
    private Ability moeMoe = new MoeMoeAbility();

    // Passif sans @RoleVariable → constructeur ✅
    private final ServeusePasive serveuse = new ServeusePasive();

    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "MOMO_ABILITY_MEMOIRE_NAME", type = VariableType.ABILITY)
    private Ability memoire = new MemoireCommand();

    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "MOMO_ABILITY_PSYCHO_NAME", type = VariableType.ABILITY)
    private Ability psycho = new PsychokinesieAbility();

    public MomoRole() {
        getAbilities().add(serveuse);
    }

    @Override public int getId()                { return 2; }
    @Override public String getName()           { return "Momo"; }
    @Override public Material getIconMaterial() { return Material.STICK; }

    @Override
    public String getDescription(Player player) {
        return net.novaproject.novauhc.lang.LangManager.get()
                .get(net.novauhc.dandadan.lang.DanDaDanLang.MOMO_DESC, player);
    }

    @Override
    public void onConsume(Player player, ItemStack item, PlayerItemConsumeEvent event) {
        super.onConsume(player, item, event);
        if (item.getType() != Material.GOLDEN_APPLE) return;
        UUID id = player.getUniqueId();
        serveuse.markEating(id);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> serveuse.unmarkEating(id), 32L);
    }
}
