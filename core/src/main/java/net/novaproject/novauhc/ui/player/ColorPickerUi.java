package net.novaproject.novauhc.ui.player;

import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.PlayerColorManager;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.novaproject.novauhc.utils.ui.item.ActionItem;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Menu de sélection de couleur pour un joueur cible.
 * La couleur choisie modifie le nom du joueur dans le TAB uniquement pour le viewer.
 */
public class ColorPickerUi extends CustomInventory {

    private final Player target;

    // Association couleur Minecraft → couleur de laine
    private static final Object[][] COLORS = {
        {ChatColor.WHITE,        DyeColor.WHITE,      "§fBlanc"},
        {ChatColor.YELLOW,       DyeColor.YELLOW,     "§eJaune"},
        {ChatColor.GOLD,         DyeColor.ORANGE,     "§6Orange"},
        {ChatColor.RED,          DyeColor.RED,        "§cRouge"},
        {ChatColor.DARK_RED,     DyeColor.BROWN,      "§4Rouge foncé"},
        {ChatColor.LIGHT_PURPLE, DyeColor.MAGENTA,    "§dViolet clair"},
        {ChatColor.DARK_PURPLE,  DyeColor.PURPLE,     "§5Violet"},
        {ChatColor.BLUE,         DyeColor.BLUE,       "§1Bleu foncé"},
        {ChatColor.AQUA,         DyeColor.CYAN,       "§bCyan"},
        {ChatColor.DARK_AQUA,    DyeColor.CYAN,       "§3Cyan foncé"},
        {ChatColor.GREEN,        DyeColor.LIME,       "§aVert"},
        {ChatColor.DARK_GREEN,   DyeColor.GREEN,      "§2Vert foncé"},
        {ChatColor.GRAY,         DyeColor.SILVER,     "§7Gris"},
        {ChatColor.DARK_GRAY,    DyeColor.GRAY,       "§8Gris foncé"},
        {ChatColor.BLACK,        DyeColor.BLACK,      "§0Noir"},
        {ChatColor.RESET,        DyeColor.WHITE,      "§fDéfaut (reset)"},
    };

    public ColorPickerUi(Player player, Player target) {
        super(player);
        this.target = target;
    }

    @Override
    public void setup() {
        fillCorner(getConfig().getInt("menu.stuff.color"));

        for (int i = 0; i < COLORS.length; i++) {
            ChatColor chatColor = (ChatColor) COLORS[i][0];
            DyeColor dyeColor  = (DyeColor)  COLORS[i][1];
            String label       = (String)    COLORS[i][2];

            @SuppressWarnings("deprecation")
            ItemCreator wool = new ItemCreator(Material.WOOL)
                    .setDurability((short) dyeColor.getWoolData())
                    .setName(label)
                    .addLore("")
                    .addLore("§8» §fCliquer pour appliquer")
                    .addLore("");

            addItem(new ActionItem(10 + i + (i / 7) * 2, wool) {
                @Override
                public void onClick(InventoryClickEvent e) {
                    if (e.getCurrentItem() == null) return;
                    if (chatColor == ChatColor.RESET) {
                        // Retirer la couleur et restaurer le tag d'origine
                        PlayerColorManager.removeColorForViewer(getPlayer(), target);
                    } else {
                        PlayerColorManager.applyColor(getPlayer(), target, chatColor);
                    }
                    getPlayer().closeInventory();
                    getPlayer().sendMessage("§8» §fCouleur de §e" + target.getName() + " §fmise à jour !");
                }
            });
        }

        addReturn(31, new ColorPlayerListUi(getPlayer()));
    }

    @Override
    public String getTitle() { return "§8Couleur TAB : §f" + target.getName(); }

    @Override
    public int getLines() { return 4; }

    @Override
    public boolean isRefreshAuto() { return false; }
}
