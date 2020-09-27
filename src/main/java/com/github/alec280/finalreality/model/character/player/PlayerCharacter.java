package com.github.alec280.finalreality.model.character.player;

import com.github.alec280.finalreality.model.character.AbstractCharacter;
import com.github.alec280.finalreality.model.character.ICharacter;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

import com.github.alec280.finalreality.model.weapon.Weapon;
import com.github.alec280.finalreality.model.weapon.WeaponType;
import org.jetbrains.annotations.NotNull;

/**
 * A class that holds all the information of a single character of the game.
 *
 * @author Ignacio Slater Muñoz.
 * @author <Your name>
 */
public class PlayerCharacter extends AbstractCharacter {

  private Weapon equippedWeapon = null;

  /**
   * Creates a new character.
   *
   * @param name
   *     the character's name
   * @param maxHealth
   *     the character's maximum health
   * @param defense
   *     the character's defense
   * @param characterClass
   *     the class of this character
   * @param turnsQueue
   *     the queue with the characters waiting for their turn
   */
  public PlayerCharacter(@NotNull String name, final int maxHealth, final int defense,
      final CharacterClass characterClass, @NotNull BlockingQueue<ICharacter> turnsQueue) {
    super(turnsQueue, characterClass, name, maxHealth, defense);
  }

  /**
   * Equips a weapon to the character.
   */
  public void equip(Weapon weapon) {
    this.equippedWeapon = weapon;
  }

  /**
   * Return this character's equipped weapon.
   */
  public Weapon getEquippedWeapon() {
    return equippedWeapon;
  }

  @Override
  public int getDamage() {
    if (getEquippedWeapon() == null) {
      return super.getDamage();
    }
    return getEquippedWeapon().getDamage();
  }

  @Override
  public int getWeight() {
    if (getEquippedWeapon() == null) {
      return super.getWeight();
    }
    return getEquippedWeapon().getWeight();
  }

  @Override
  public boolean canEquip(Weapon weapon) {
    final CharacterClass charClass = getCharacterClass();
    final WeaponType type = weapon.getType();
    if (charClass == CharacterClass.KNIGHT) {
      return type == WeaponType.SWORD ||
        type == WeaponType.AXE ||
        type == WeaponType.KNIFE;
    } else if (charClass == CharacterClass.ENGINEER) {
      return type == WeaponType.AXE ||
        type == WeaponType.BOW;
    } else if (charClass == CharacterClass.THIEF) {
      return type == WeaponType.STAFF ||
        type == WeaponType.BOW;
    } else if (charClass == CharacterClass.BLACK_MAGE) {
      return type == WeaponType.KNIFE ||
        type == WeaponType.STAFF;
    } else if (charClass == CharacterClass.WHITE_MAGE) {
      return type == WeaponType.STAFF;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCharacterClass());
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PlayerCharacter)) {
      return false;
    }
    final PlayerCharacter that = (PlayerCharacter) o;
    return getCharacterClass() == that.getCharacterClass()
        && getName().equals(that.getName());
  }
}
