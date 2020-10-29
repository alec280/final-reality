package com.github.alec280.finalreality.model.character;

import com.github.alec280.finalreality.model.weapon.IWeapon;
import org.jetbrains.annotations.NotNull;

/**
 * This represents a character from the game.
 * A character can be controlled by the player or by the CPU (an enemy).
 *
 * @author Ignacio Slater Muñoz
 * @author Alexander Cuevas.
 */
public interface ICharacter {

  /**
   * Sets a scheduled executor to make this character (thread) wait for {@code speed / 10}
   * seconds before adding the character to the queue.
   */
  void waitTurn();

  /**
   * Returns this character's name.
   */
  String getName();

  /**
   * Returns this character's maximum health.
   */
  int getMaxHealth();

  /**
   * Returns this character's current health.
   */
  int getHealth();

  /**
   * Sets this character's current health to a value between 0 and its maximum health.
   */
  void setHealth(final int value);

  /**
   * Returns this character's defense.
   */
  int getDefense();

  /**
   * Returns this character's attack.
   */
  int getAttack();

  /**
   * Returns this character's weight.
   */
  int getWeight();

  /**
   * Returns true if this character can equip the given weapon.
   */
  boolean canEquip(@NotNull IWeapon weapon);

  /**
   * Returns true if this character's current health is greater than zero.
   */
  boolean isAlive();

  /**
   * This character deals damage to another character, the target must be alive.
   */
  void doDamage(@NotNull ICharacter target);

}
