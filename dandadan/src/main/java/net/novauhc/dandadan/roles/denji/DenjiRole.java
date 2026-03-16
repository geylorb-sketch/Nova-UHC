package net.novauhc.dandadan.roles.denji;

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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DenjiRole extends DanDaDanRole {

    private int blood = 100;
    private final int MAX_BLOOD = 100;
    private int chainsawCounter = 0; // 0-10 par cible
    private final Map<UUID, Integer> targetCounters = new HashMap<>();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DENJI_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability malediction = new DenjiMalediction();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DENJI_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability chaine = new ChaineAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DENJI_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability chainsawMan = new ChainsawManAbility();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DENJI_PASSIVE_SANG_NAME", type = VariableType.ABILITY)
    private Ability sangPassive = new SangPassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DENJI_PASSIVE_OUBLIE_NAME", type = VariableType.ABILITY)
    private Ability oubliePassive = new OubliePassive();

public DenjiRole() {
        setCamp(DanDaDanCamps.SOLO);
    }

    @Override public int getId()                { return 27; }
    @Override public String getName()           { return "Denji"; }
    @Override public Material getIconMaterial() { return Material.IRON_AXE; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLangExt3.DENJI_DESC, player);
    }

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        // Oublie : stuff tombe dans l'inventaire
    }

    public int getBlood() { return blood; }
    public void addBlood(int b) { blood = Math.min(MAX_BLOOD, blood + b); }
    public boolean useBlood(int b) { if (blood < b) return false; blood -= b; return true; }

    public int getTargetCounter(UUID uuid) { return targetCounters.getOrDefault(uuid, 0); }
    public void incrementTarget(UUID uuid) { targetCounters.merge(uuid, 1, Integer::sum); }
    public void resetTarget(UUID uuid) { targetCounters.remove(uuid); }

    // ── Passif Sang ──

    // ── Passif Oublie : loot auto ──

    // ── Malédiction (casque spécial + force/résistance/speed) ──

    // ── Chaine ──

    // ── Chainsaw Man (100🩸) ──
}

// ════════════════════════════════════════════
//  Reze (id 28)
// ════════════════════════════════════════════
