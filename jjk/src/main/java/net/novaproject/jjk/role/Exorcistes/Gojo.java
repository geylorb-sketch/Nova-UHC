package net.novaproject.jjk.role.Exorcistes;

import net.novaproject.jjk.JJKCamp;
import net.novaproject.jjk.JJKRole;
import net.novaproject.jjk.lang.JJKLang;
import net.novaproject.jjk.pouvoir.Blue;
import net.novaproject.jjk.utils.MessageUtils;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.scenario.role.RoleDescription;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Gojo extends JJKRole {

    @RoleVariable(lang = JJKLang.class, nameKey = "GOJO_BLUE_NAME", type = VariableType.ABILITY)
    Ability blue = new Blue();

    public Gojo() {
        setCamp(JJKCamp.EXORCISTES);
    }

    @Override
    public String getName() {
        return "Gojo";
    }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .raw("§7▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")
            .space()
            .raw("§e● §fInformations")
            .raw("§7Rôle : §aGojo")
            .raw("§7Objectif : §fGagner avec le camp §aExorcistes")
            .space()
            .raw("§e● §fVotre rôle §7(§fpasse la souris§7)")
            .send();
        MessageUtils.sendHoverLine(p, "§7• §dÉnergie occulte : §51000❂", "§fÉnergie utilisée pour lancer vos techniques.");
        MessageUtils.sendHoverLine(p, "§7• §aPassif", "§fGojo maîtrise l'infini et possède une très grande puissance.");
        RoleDescription.of(p)
            .space()
            .raw("§e● §fCapacités §7(§fpasse la souris§7)")
            .send();
        MessageUtils.sendHoverLine(p, "§9• §fBlue", "§fAttire ou manipule l'espace autour de la cible.");
        RoleDescription.of(p)
            .space()
            .raw("§7▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬")
            .send();
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.BONE);
    }



}