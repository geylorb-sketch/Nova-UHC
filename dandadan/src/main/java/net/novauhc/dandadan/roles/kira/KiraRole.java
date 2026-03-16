package net.novauhc.dandadan.roles.kira;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanCamps;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public class KiraRole extends DanDaDanRole {

    private boolean standActive = false;
    private final Set<UUID>    explodedPlayers  = new HashSet<>();
    private final Map<UUID, Location> lastPositions = new HashMap<>();
    private int   mainHits  = 0;
    private boolean mainActive = false;
    private int   currentEnchant = 3;

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KIRA_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability strayCat = new StrayCatAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KIRA_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability bombe = new BombeKiraAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KIRA_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability killerQueen = new KillerQueenAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KIRA_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability sheerHeartAttack = new SheerHeartAttackAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KIRA_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability bitesDust = new BitesDustAbility();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KIRA_PASSIVE_MAIN_NAME", type = VariableType.ABILITY)
    private Ability mainPassive = new MainPassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KIRA_PASSIVE_EXPLOSION_IMMUNE_NAME", type = VariableType.ABILITY)
    private Ability explosionImmunePassive = new ExplosionImmunePassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KIRA_PASSIVE_NAME_NAME", type = VariableType.ABILITY)
    private Ability kiraNamePassive = new KiraNamePassive();

public KiraRole() {
        setCamp(DanDaDanCamps.SOLO);
    }

    @Override public int getId()                { return 31; }
    @Override public String getName()           { return "Kira"; }
    @Override public Material getIconMaterial() { return Material.TNT; }
    @Override public String getDescription(Player player) { return LangManager.get().get(DanDaDanLangExt3.KIRA_DESC, player); }

    @Override public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        if (victim.getPlayer() == null) return;
        // Épée volée apparaît en l'air
        mainActive = true; mainHits = 0; currentEnchant++;
        LangManager.get().send(DanDaDanLangExt3.KIRA_MAIN_PASSIVE, killer.getPlayer() != null ? killer.getPlayer() : null);
    }

    public boolean isMainActive() { return mainActive; }
    public void setMainActive(boolean mainActive) { this.mainActive = mainActive; }
    public int getMainHits() { return mainHits; }
    public void setMainHits(int mainHits) { this.mainHits = mainHits; }
    public void addMainHit() { this.mainHits++; }

    public boolean isStandActive() { return standActive; }
    public void setStandActive(boolean b) { standActive = b; }
    public boolean isExploded(UUID id) { return explodedPlayers.contains(id); }
    public void addExploded(UUID id, Player victim) {
        explodedPlayers.add(id);
        lastPositions.put(id, victim.getLocation().clone());
        victim.sendMessage(LangManager.get().get(DanDaDanLangExt3.KIRA_EXPLODED_STATUS, victim));
    }
    public Set<UUID> getExplodedPlayers() { return explodedPlayers; }
    public Map<UUID, Location> getLastPositions() { return lastPositions; }
    public void recordPosition(UUID id, Location loc) { lastPositions.put(id, loc); }

    // Passif Main

    // Passif immunité explosion

    // Passif Kira name change

    // Stray Cat

    // Bombe

    // Killer Queen (Stand)

    // Sheer Heart Attack

    // Bites the Dust
}

// ════════════════════════════════════════════
//  Polnareff (id 32)
// ════════════════════════════════════════════
