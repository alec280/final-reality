package com.github.alec280.finalreality.model.weapon;

import java.util.Objects;

/**
 * A class that holds all the information of a single bow.
 *
 * @author Alexander Cuevas.
 */
public class Bow extends AbstractWeapon {

  /**
   * Creates a new Bow weapon.
   *
   * @param name
   *     the name of the bow
   * @param attack
   *     the attack of the bow
   * @param weight
   *     the weight of the bow
   */
  public Bow(final String name, final int attack, final int weight) {
    super(name, attack, weight);
  }

  @Override
  public boolean canBeEquippedToEngineer() {
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
    if (!(o instanceof Bow)) {
      return false;
    }
    final Bow bow = (Bow) o;
    return getName().equals(bow.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getClass());
  }

}
