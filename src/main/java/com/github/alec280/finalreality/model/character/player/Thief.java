package com.github.alec280.finalreality.model.character.player;

import com.github.alec280.finalreality.model.character.CharacterClass;
import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.weapon.IWeapon;
import com.github.alec280.finalreality.model.weapon.WeaponType;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.BlockingQueue;

/**
 * A class that holds all the information of a single Thief.
 *
 * @author Alexander Cuevas.
 */
public class Thief extends AbstractPlayer {

  /**
   * Creates a new Thief player.
   *
   * @param name
   *     the thief's name
   * @param maxHealth
   *     the thief's maximum health
   * @param defense
   *     the thief's defense
   * @param turnsQueue
   *     the queue with the characters waiting for their turn
   */
  public Thief(@NotNull String name, int maxHealth, int defense,
      @NotNull BlockingQueue<ICharacter> turnsQueue) {
    super(name, maxHealth, defense, CharacterClass.THIEF, turnsQueue);
  }

  @Override
  public boolean canEquip(IWeapon weapon) {
    return weapon.getType() == WeaponType.SWORD ||
      weapon.getType() == WeaponType.STAFF ||
      weapon.getType() == WeaponType.BOW;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Thief)) {
      return false;
    }
    final Thief thief = (Thief) o;
    return getName().equals(thief.getName());
  }

}
