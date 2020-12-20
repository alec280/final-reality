package com.github.alec280.finalreality.model.weapon;

import java.util.Objects;

/**
 * A class that holds all the information of a single sword.
 *
 * @author Alexander Cuevas.
 */
public class Sword extends AbstractWeapon {

  /**
   * Creates a new Sword weapon.
   *
   * @param name
   *     the name of the sword
   * @param attack
   *     the attack of the sword
   * @param weight
   *     the weight of the sword
   */
  public Sword(final String name, final int attack, final int weight) {
    super(name, attack, weight);
  }

  @Override
  public String getSpriteName() {
    return "sword";
  }

  @Override
  public boolean canBeEquippedToKnight() {
    return true;
  }

  @Override
  public boolean canBeEquippedToThief() {
    return true;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Sword)) {
      return false;
    }
    final Sword sword = (Sword) o;
    return getName().equals(sword.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getClass());
  }

}
