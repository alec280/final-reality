package com.github.alec280.finalreality.model.character.player;

import com.github.alec280.finalreality.model.character.CharacterClass;
import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.weapon.IWeapon;
import com.github.alec280.finalreality.model.weapon.WeaponType;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.BlockingQueue;

/**
 * A class that holds all the information of a single engineer.
 *
 * @author Alexander Cuevas.
 */
public class Engineer extends AbstractPlayer {

  /**
   * Creates a new Engineer player.
   *
   * @param name
   *     the engineer's name
   * @param maxHealth
   *     the engineer's maximum health
   * @param defense
   *     the engineer's defense
   * @param turnsQueue
   *     the queue with the characters waiting for their turn
   */
  public Engineer(@NotNull String name, int maxHealth, int defense,
      @NotNull BlockingQueue<ICharacter> turnsQueue) {
    super(name, maxHealth, defense, CharacterClass.ENGINEER, turnsQueue);
  }

  @Override
  public boolean canEquip(IWeapon weapon) {
    return weapon.getType() == WeaponType.AXE ||
      weapon.getType() == WeaponType.BOW;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Engineer)) {
      return false;
    }
    final Engineer engineer = (Engineer) o;
    return getName().equals(engineer.getName());
  }

}
