package net.novaproject.novauhc.ability.template;

import net.novaproject.novauhc.uhcplayer.UHCPlayer;

/** @deprecated Use {@link BowAbility} instead */
@Deprecated
public abstract class BowAbbility extends BowAbility {
    public BowAbbility(UHCPlayer target) {
        setTarget(target);
    }
    public BowAbbility() {
    }
}
