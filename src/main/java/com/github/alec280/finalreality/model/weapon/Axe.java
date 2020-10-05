package com.github.alec280.finalreality.model.weapon;

import java.util.Objects;

/**
 * A class that holds all the information of a single axe.
 *
 * @author Alexander Cuevas.
 */
public class Axe extends AbstractWeapon {

  /**
   * Creates a new Axe weapon.
   *
   * @param name
   *     the name of the axe
   * @param damage
   *     the damage of the axe
   * @param weight
   *     the weight of the axe
   */
  public Axe(final String name, final int damage, final int weight) {
    super(name, damage, weight);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Axe)) {
      return false;
    }
    final Axe axe = (Axe) o;
    return getName().equals(axe.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getClass());
  }

}
