package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;

public class ChatPvP extends Scenario {
    @Override
    public String getName() {
        return "Chat PvP";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.CHAT_PVP, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.BOOK_AND_QUILL);
    }

    @Override
    public void onSec(Player p) {
        int timer = UHCManager.get().getTimer();
        int timerpvp = UHCManager.get().getTimerpvp();
        if (timer == timerpvp) {
            UHCManager.get().setChatdisbale(true);
            LangManager.get().send(CommonLang.CHAT_DISABLED, p);
        }

    }
}
