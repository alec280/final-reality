package com.github.alec280.finalreality.model.weapon;

import java.util.Objects;

/**
 * A class that holds all the information of a single staff.
 *
 * @author Alexander Cuevas.
 */
public class Staff extends AbstractWeapon {

  private final int magicAttack;

  /**
   * Creates a new Staff weapon.
   *
   * @param name
   *     the name of the staff
   * @param damage
   *     the damage of the staff
   * @param weight
   *     the weight of the staff
   * @param magicAttack
   *     the magic attack of the staff
   */
  public Staff(final String name, final int damage, final int weight, final int magicAttack) {
    super(name, damage, weight);
    this.magicAttack = magicAttack;
  }

  @Override
  public boolean canBeEquippedToBlackMage() {
    return true;
  }

  @Override
  public boolean canBeEquippedToWhiteMage() {
    return true;
  }

  @Override
  public boolean canBeEquippedToThief() {
    return true;
  }

  @Override
  public int getMagicAttack() {
    return magicAttack;
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

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getClass());
  }

}
