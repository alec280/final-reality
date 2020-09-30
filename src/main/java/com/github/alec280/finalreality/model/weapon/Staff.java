package com.github.alec280.finalreality.model.weapon;

/**
 * A class that holds all the information of a single staff.
 *
 * @author Alexander Cuevas.
 */
public class Staff extends AbstractWeapon {

  private final int magicDamage;

  /**
   * Creates a new Staff weapon.
   *
   * @param name
   *     the name of the staff
   * @param damage
   *     the damage of the staff
   * @param weight
   *     the weight of the staff
   * @param magicDamage
   *     the magic damage of the staff
   */
  public Staff(final String name, final int damage, final int weight, final int magicDamage) {
    super(name, damage, weight, WeaponType.STAFF);
    this.magicDamage = magicDamage;
  }

  @Override
  public int getMagicDamage() {
    return magicDamage;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Staff)) {
      return false;
    }
    final Staff staff = (Staff) o;
    return getName().equals(staff.getName());
  }

}
