package com.github.alec280.finalreality.model.character;

import com.github.alec280.finalreality.model.character.player.CharacterClass;
import com.github.alec280.finalreality.model.weapon.Weapon;

/**
 * This represents a character from the game.
 * A character can be controlled by the player or by the CPU (an enemy).
 *
 * @author Ignacio Slater Muñoz.
 * @author <Your name>
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
   * Returns this character's defense.
   */
  int getDefense();

  /**
   * Returns this character's damage.
   */
  int getDamage();

  /**
   * Returns this character's weight.
   */
  int getWeight();

  /**
   * Returns true if this character can be equipped with a weapon.
   */
  boolean canEquip(Weapon weapon);

  /**
   * Returns this character's class.
   */
  CharacterClass getCharacterClass();
}
