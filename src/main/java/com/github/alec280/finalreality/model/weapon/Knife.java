package com.github.alec280.finalreality.model.weapon;

import java.util.Objects;

/**
 * A class that holds all the information of a single knife.
 *
 * @author Alexander Cuevas.
 */
public class Knife extends AbstractWeapon {

  /**
   * Creates a new Knife weapon.
   *
   * @param name
   *     the name of the knife
   * @param attack
   *     the attack of the knife
   * @param weight
   *     the weight of the knife
   */
  public Knife(final String name, final int attack, final int weight) {
    super(name, attack, weight);
  }

  @Override
  public String getSpriteName() {
    return "knife";
  }

  @Override
  public boolean canBeEquippedToBlackMage() {
    return true;
  }

  @Override
  public boolean canBeEquippedToKnight() {
    return true;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Knife)) {
      return false;
    }
    final Knife knife = (Knife) o;
    return getName().equals(knife.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getClass());
  }

}
