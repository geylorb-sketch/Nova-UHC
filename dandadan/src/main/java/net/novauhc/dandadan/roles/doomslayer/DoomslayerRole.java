package net.novauhc.dandadan.roles.doomslayer;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanCamps;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DoomslayerRole extends DanDaDanRole {

    public enum WeaponMode { NONE, SHOTGUN, BFG, BALISTE, LANCE_FLAMME }
    public enum BladeMode  { BERZERKER, CREUSET }

    private WeaponMode weapon    = WeaponMode.NONE;
    private BladeMode  blade     = BladeMode.CREUSET;
    private int        greenArmor = 5;   // 💚
    private double     forceBonus = 0;   // % force cumulé par kills voisins
    private int        killsForWeapon = 0; // 1 arme par kill

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DOOMSLAYER_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability weaponCmd = new WeaponCommand();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DOOMSLAYER_PASSIVE_DOOM_NAME", type = VariableType.ABILITY)
    private Ability doomPassive = new DoomPassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DOOMSLAYER_PASSIVE_CRUCIBLE_NAME", type = VariableType.ABILITY)
    private Ability cruciblBlade = new CruciblBlade();

public DoomslayerRole() {
        setCamp(DanDaDanCamps.SOLO);
    }

    @Override public int getId()                { return 26; }
    @Override public String getName()           { return "Doomslayer"; }
    @Override public Material getIconMaterial() { return Material.IRON_SWORD; }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player p = uhcPlayer.getPlayer(); if (p == null) return;
        // Épée de base : Crucible Blade Sharp III
        ItemStack sword = makeSword(3);
        p.getInventory().setItem(0, sword);
        super.onGive(uhcPlayer);
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLangExt3.DOOM_DESC, player);
    }

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        Player p = killer.getPlayer(); if (p == null) return;
        killsForWeapon++;
        // Vol des effets permanents du mort (¼) si >= 1 💚
        if (greenArmor >= 1 && victim.getPlayer() != null) {
            victim.getPlayer().getActivePotionEffects().forEach(e -> {
                int amp = Math.max(0, (e.getAmplifier() - 3) / 4);
                if (amp >= 0) p.addPotionEffect(new PotionEffect(e.getType(), e.getDuration(), amp));
            });
        }
        // +1❤ si 💚 et victime pleine vie (>50%)
        if (greenArmor >= 1) p.setHealth(Math.min(p.getMaxHealth(), p.getHealth() + 2));
        p.sendMessage("§c[Doom] §71 weapon token disponible (/ddd weapon)");
    }

    public int getGreenArmor() { return greenArmor; }
    public void hitGreenArmor() {
        if (greenArmor > 0) {
            greenArmor--;
            Player p = getHolder(); if (p == null) return;
            p.sendMessage("§c💚 §7Armure verte : §f" + greenArmor + "/5");
            if (greenArmor == 0) {
                LangManager.get().send(DanDaDanLangExt3.DOOM_ARMOR_LOST, p);
                // +1 prot pendant 5s
                ItemStack[] armor = p.getInventory().getArmorContents();
                for (ItemStack piece : armor) {
                    if (piece != null && piece.getType().name().contains("DIAMOND")) {
                        int lvl = piece.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                        piece.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, lvl + 1);
                    }
                }
                Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
                    for (ItemStack piece : p.getInventory().getArmorContents()) {
                        if (piece != null && piece.getType().name().contains("DIAMOND")) {
                            int lvl = piece.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                            if (lvl > 0) piece.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, Math.max(0, lvl - 1));
                        }
                    }
                }, 100L);
            }
        }
    }

    public WeaponMode getWeapon()   { return weapon; }
    public void setWeapon(WeaponMode w) { weapon = w; killsForWeapon = 0; }
    public BladeMode getBladeMode() { return blade; }
    public void toggleBlade() {
        blade = (blade == BladeMode.BERZERKER) ? BladeMode.CREUSET : BladeMode.BERZERKER;
        Player p = getHolder(); if (p == null) return;
        if (blade == BladeMode.BERZERKER) {
            LangManager.get().send(DanDaDanLangExt3.DOOM_BLADE_BERZERKER, p);
            p.getInventory().setItem(0, makeSword(4));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false));
        } else {
            LangManager.get().send(DanDaDanLangExt3.DOOM_BLADE_CREUSET, p);
            p.getInventory().setItem(0, makeSword(4));
        }
    }
    public boolean hasKillToken() { return killsForWeapon > 0; }
    public double getForceBonus() { return forceBonus; }

    private static ItemStack makeSword(int sharpness) {
        ItemStack s = new ItemStack(Material.DIAMOND_SWORD);
        s.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, sharpness);
        ItemMeta m = s.getItemMeta();
        assert m != null;
        m.setDisplayName("§4Crucible Blade");
        s.setItemMeta(m);
        return s;
    }

    private Player getHolder() {
        return Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.getInventory().contains(Material.DIAMOND_SWORD))
                .findFirst().orElse(null);
    }

    // ── Passif global ──

    // ── Crucible Blade (drop pour changer mode) ──

    // /ddd weapon
}

// ════════════════════════════════════════════
//  Denji (id 27)
// ════════════════════════════════════════════
