package net.novauhc.dandadan;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.Role;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class DanDaDanRole extends Role {

    public DanDaDanRole() {
        setCamp(DanDaDanCamps.SOLO);
    }

    public abstract int getId();
    public abstract Material getIconMaterial();
    public abstract String getDescription(Player player);

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player == null) return;

        String msg = LangManager.get().get(DanDaDanLang.YOKAI_OBTAINED, player)
                .replace("%yokai%", getName());
        player.sendMessage(msg);
        player.sendMessage(getDescription(player));

        // NE PAS appeler getAbilities().add() ici —
        // RoleVariableProcessor ajoute les abilities @RoleVariable(type=ABILITY)
        // super.onGive() déclenche onGive() de toutes les abilities déjà dans le Set
        super.onGive(uhcPlayer);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(getIconMaterial());
    }

    @Override
    public void sendDescription(Player player) {
        player.sendMessage(getDescription(player));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return getId() == ((DanDaDanRole) obj).getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(getId());
    }
}
