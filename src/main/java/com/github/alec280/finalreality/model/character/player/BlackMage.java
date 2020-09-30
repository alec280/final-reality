package com.github.alec280.finalreality.model.character.player;

import com.github.alec280.finalreality.model.character.CharacterClass;
import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.weapon.IWeapon;
import com.github.alec280.finalreality.model.weapon.WeaponType;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.BlockingQueue;

/**
 * A class that holds all the information of a single black mage.
 *
 * @author Alexander Cuevas.
 */
public class BlackMage extends AbstractMage {

  /**
   * Creates a new Black Mage player.
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
  public BlackMage(@NotNull String name, int maxHealth, int defense, int maxMana,
      @NotNull BlockingQueue<ICharacter> turnsQueue) {
    super(name, maxHealth, defense, maxMana, CharacterClass.BLACK_MAGE, turnsQueue);
  }

  /**
   * Creates a new Black Mage player without mana.
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
  public BlackMage(@NotNull String name, int maxHealth, int defense,
      @NotNull BlockingQueue<ICharacter> turnsQueue) {
    super(name, maxHealth, defense, 0, CharacterClass.BLACK_MAGE, turnsQueue);
  }

  @Override
  public boolean canEquip(IWeapon weapon) {
    return weapon.getType() == WeaponType.KNIFE ||
      weapon.getType() == WeaponType.STAFF;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BlackMage)) {
      return false;
    }
    final BlackMage mage = (BlackMage) o;
    return getName().equals(mage.getName());
  }

}