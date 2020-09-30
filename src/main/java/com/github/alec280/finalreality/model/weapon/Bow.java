package com.github.alec280.finalreality.model.weapon;

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
   * @param damage
   *     the damage of the bow
   * @param weight
   *     the weight of the bow
   */
  public Bow(final String name, final int damage, final int weight) {
    super(name, damage, weight, WeaponType.BOW);
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
  
}
