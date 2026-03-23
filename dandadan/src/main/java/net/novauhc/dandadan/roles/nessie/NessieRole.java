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
import net.novaproject.novauhc.scenario.role.RoleDescription;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class NessieRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "NESSIE_ABILITY_DELUGE_NAME", type = VariableType.ABILITY)
    private Ability delugeAbility = new DelugeAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "NESSIE_ABILITY_JETEAU_NAME", type = VariableType.ABILITY)
    private Ability jetEauAbility = new JetEauAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "NESSIE_ABILITY_POISSON_NAME", type = VariableType.ABILITY)
    private Ability poissonPassive = new PoissonPassive();


    public NessieRole() {
    }

    @Override public String getName() { return "Nessie"; }
    @Override public Material getIconMaterial() { return Material.WATER_BUCKET; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.NESSIE_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.NESSIE_POISSON_TEXT, DanDaDanDescLang.NESSIE_POISSON_HOVER)
            .hover(DanDaDanDescLang.NESSIE_COU_L_TEXT, DanDaDanDescLang.NESSIE_COU_L_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.NESSIE_DELUGE_TEXT, DanDaDanDescLang.NESSIE_DELUGE_HOVER)
            .hover(DanDaDanDescLang.NESSIE_JET_E_TEXT, DanDaDanDescLang.NESSIE_JET_E_HOVER)
            .hover(DanDaDanDescLang.NESSIE_COU_TEXT, DanDaDanDescLang.NESSIE_COU_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
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
