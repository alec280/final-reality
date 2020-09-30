package com.github.alec280.finalreality.model.character.player;

import com.github.alec280.finalreality.model.character.CharacterClass;
import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.weapon.IWeapon;
import com.github.alec280.finalreality.model.weapon.WeaponType;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.BlockingQueue;

/**
 * A class that holds all the information of a single white mage.
 *
 * @author Alexander Cuevas.
 */
public class WhiteMage extends AbstractMage {

  /**
   * Creates a new White Mage player.
   *
   * @param name
   *     the mage's name
   * @param maxHealth
   *     the mage's maximum health
   * @param defense
   *     the mage's defense
   * @param maxMana
   *     the mage's maximum mana
   * @param turnsQueue
   *     the queue with the characters waiting for their turn
   */
  public WhiteMage(@NotNull String name, int maxHealth, int defense, int maxMana,
      @NotNull BlockingQueue<ICharacter> turnsQueue) {
    super(name, maxHealth, defense, maxMana, CharacterClass.WHITE_MAGE, turnsQueue);
  }

  /**
   * Creates a new White Mage player without mana.
   *
   * @param name
   *     the mage's name
   * @param maxHealth
   *     the mage's maximum health
   * @param defense
   *     the mage's defense
   * @param turnsQueue
   *     the queue with the characters waiting for their turn
   */
  public WhiteMage(@NotNull String name, int maxHealth, int defense,
      @NotNull BlockingQueue<ICharacter> turnsQueue) {
    super(name, maxHealth, defense, 0, CharacterClass.WHITE_MAGE, turnsQueue);
  }

  @Override
  public boolean canEquip(IWeapon weapon) {
    return weapon.getType() == WeaponType.STAFF;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof WhiteMage)) {
      return false;
    }
    final WhiteMage mage = (WhiteMage) o;
    return getName().equals(mage.getName());
  }

}
