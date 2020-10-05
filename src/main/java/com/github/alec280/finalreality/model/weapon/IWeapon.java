package com.github.alec280.finalreality.model.weapon;

/**
 * This represents a weapon from the game.
 *
 * @author Alexander Cuevas.
 */
public interface IWeapon {

  /**
   * Returns the name of this weapon.
   */
  String getName();

  /**
   * Returns the damage of this weapon.
   */
  int getDamage();

  /**
   * Returns the magic damage of this weapon.
   */
  int getMagicDamage();

  /**
   * Returns the weight of this weapon.
   */
  int getWeight();

}
