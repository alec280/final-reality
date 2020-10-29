package com.github.alec280.finalreality.model.weapon;

/**
 * An abstract class that holds the common behaviour of all the weapons in the game.
 *
 * @author Alexander Cuevas.
 */
public abstract class AbstractWeapon implements IWeapon {

  private final String name;
  private final int attack;
  private final int weight;

  protected AbstractWeapon(final String name, final int attack, final int weight) {
    this.name = name;
    this.attack = attack;
    this.weight = weight;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getAttack() {
    return attack;
  }

  @Override
  public int getMagicAttack() {
    return getAttack() / 5;
  }

  @Override
  public int getWeight() {
    return weight;
  }

  @Override
  public boolean canBeEquippedToBlackMage() {
    return false;
  }

  @Override
  public boolean canBeEquippedToWhiteMage() {
    return false;
  }

  @Override
  public boolean canBeEquippedToEngineer() {
    return false;
  }

  @Override
  public boolean canBeEquippedToKnight() {
    return false;
  }

  @Override
  public boolean canBeEquippedToThief() {
    return false;
  }
}
