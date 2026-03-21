package net.novauhc.dandadan.roles.nessie;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanDescLang;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novaproject.novauhc.utils.HoverUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class NessieRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "NESSIE_ABILITY_DELUGE_NAME", type = VariableType.ABILITY)
    private Ability delugeAbility = new DelugeAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "NESSIE_ABILITY_JETEAU_NAME", type = VariableType.ABILITY)
    private Ability jetEauAbility = new JetEauAbility();

    private final PoissonPassive poissonPassive  = new PoissonPassive();


    public NessieRole() {
        getAbilities().add(poissonPassive);
    }

    @Override public String getName() { return "Nessie"; }
    @Override public Material getIconMaterial() { return Material.WATER_BUCKET; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.NESSIE_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.NESSIE_POISSON_TEXT), L(DanDaDanDescLang.NESSIE_POISSON_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.NESSIE_COU_L_TEXT), L(DanDaDanDescLang.NESSIE_COU_L_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.NESSIE_DELUGE_TEXT), L(DanDaDanDescLang.NESSIE_DELUGE_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.NESSIE_JET_E_TEXT), L(DanDaDanDescLang.NESSIE_JET_E_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.NESSIE_COU_TEXT), L(DanDaDanDescLang.NESSIE_COU_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.WATER_BUCKET).setName(LangManager.get().get(DanDaDanLang.ITEM_NESSIE_9DELUGE)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.PRISMARINE_SHARD).setName(LangManager.get().get(DanDaDanLang.ITEM_NESSIE_BJET_DEAU)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
