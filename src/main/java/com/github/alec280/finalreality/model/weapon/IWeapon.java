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
   * Return the name of the sprite that represents this weapon.
   */
  String getSpriteName();

  /**
   * Returns the attack of this weapon.
   */
  int getAttack();

  /**
   * Returns the magic attack of this weapon.
   */
  int getMagicAttack();

  /**
   * Returns the weight of this weapon.
   */
  int getWeight();

  /**
   * Returns true if this weapon can be equipped to a black mage.
   */
  boolean canBeEquippedToBlackMage();

  /**
   * Returns true if this weapon can be equipped to a white mage.
   */
  boolean canBeEquippedToWhiteMage();

  /**
   * Returns true if this weapon can be equipped to an engineer.
   */
  boolean canBeEquippedToEngineer();

  /**
   * Returns true if this weapon can be equipped to a knight.
   */
  boolean canBeEquippedToKnight();

  /**
   * Returns true if this weapon can be equipped to a thief.
   */
  boolean canBeEquippedToThief();

}
