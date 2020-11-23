package com.github.alec280.finalreality.model.character.player;

import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.weapon.IWeapon;
import org.jetbrains.annotations.NotNull;

/**
 * This represents a player character from the game.
 * A player character can be controlled by the player.
 *
 * @author Alexander Cuevas.
 */
public interface IPlayerCharacter extends ICharacter {

    /**
     * Returns true if this player can equip the given weapon.
     */
    boolean canEquip(@NotNull IWeapon weapon);

    /**
     * Equips a weapon to the player.
     */
    void equip(@NotNull IWeapon weapon);

    /**
     * Return this player's equipped weapon.
     */
    IWeapon getEquippedWeapon();
}
