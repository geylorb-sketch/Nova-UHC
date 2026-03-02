package net.novaproject.ultimate.taupegun;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.special.TaupeGunLang;
import net.novaproject.novauhc.ui.ConfigVarUi;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Map;

public class TaupeGunUI extends CustomInventory {

    public TaupeGunUI(Player player) {
        super(player);
    }

    @Override
    public void setup() {
        TaupeGun taupeGun = TaupeGun.getInstance();
        fillLine(1, 14);

        String moleLore = LangManager.get().get(TaupeGunLang.UI_MOLE_COUNT_LORE, getPlayer(),
                Map.of("%value%", String.valueOf(taupeGun.getMole())));
        ItemCreator moleItem = new ItemCreator(Material.BONE)
                .setName(LangManager.get().get(TaupeGunLang.UI_MOLE_COUNT_NAME, getPlayer()))
                .setLores(Arrays.asList(moleLore.split("\n")));

        addMenu(2, moleItem, new ConfigVarUi(getPlayer(), 3, 2, 1, 3, 2, 1, taupeGun.getMole(), 1, 6, this) {
            @Override
            public void onChange(Number newValue) {
                taupeGun.setMole((int) newValue);
            }
        });

        String teamLore = LangManager.get().get(TaupeGunLang.UI_MOLE_TEAM_SIZE_LORE, getPlayer(),
                Map.of("%value%", String.valueOf(taupeGun.getMolesize())));
        ItemCreator teamItem = new ItemCreator(Material.BOOK)
                .setName(LangManager.get().get(TaupeGunLang.UI_MOLE_TEAM_SIZE_NAME, getPlayer()))
                .setLores(Arrays.asList(teamLore.split("\n")));

        addMenu(4, teamItem, new ConfigVarUi(getPlayer(), 3, 2, 1, 3, 2, 1, taupeGun.getMolesize(), 1, 6, this) {
            @Override
            public void onChange(Number newValue) {
                taupeGun.setMolesize((int) newValue);
            }
        });
    }

    @Override
    public String getTitle() {
        return LangManager.get().get(TaupeGunLang.UI_TITLE, getPlayer());
    }

    @Override
    public int getLines() {
        return 1;
    }

    @Override
    public boolean isRefreshAuto() {
        return false;
    }
}