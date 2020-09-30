package com.github.alec280.finalreality.model.weapon;

import java.util.Objects;

/**
 * An abstract class that holds the common behaviour of all the weapons in the game.
 *
 * @author Alexander Cuevas.
 */
public abstract class AbstractWeapon implements IWeapon {

  private final String name;
  private final int damage;
  private final int weight;
  private final WeaponType type;

  protected AbstractWeapon(final String name, final int damage, final int weight, final WeaponType type) {
    this.name = name;
    this.damage = damage;
    this.weight = weight;
    this.type = type;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getDamage() {
    return damage;
  }

  @Override
  public int getMagicDamage() {
    return getDamage() / 5;
  }

  @Override
  public int getWeight() {
    return weight;
  }

  @Override
  public WeaponType getType() {
    return type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getType());
  }

}
