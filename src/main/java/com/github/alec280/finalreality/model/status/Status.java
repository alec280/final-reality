package com.github.alec280.finalreality.model.status;

import java.util.Objects;

/**
 * A class that holds all the information of a status effect.
 *
 * @author Alexander Cuevas.
 */
public class Status {

  private final StatusType type;
  private final int damage;
  private final boolean oneShot;

  /**
   * Creates a status effect of a certain type, its damage depends on
   * its type and magic damage given.
   *
   * @see StatusType
   */
  public Status(final StatusType type, final int magicDamage) {
    this.type = type;
    this.damage = calculateDamage(magicDamage);
    this.oneShot = type == StatusType.PARALYSIS;
  }

  private StatusType getType() {
    return type;
  }

  public int getDamage() {
    return damage;
  }

  public boolean isOneShot() {
    return oneShot;
  }

  /**
   * Returns the damage of this status effect based on its type.
   */
  private int calculateDamage(final int magicDamage) {
    if (getType() == StatusType.POISON) {
      return magicDamage / 3;
    }
    if (getType() == StatusType.BURN) {
      return magicDamage / 2;
    }
    return 0;
  }

  /**
   * Returns true if this status effect can prevent a character from attacking.
   */
  public boolean preventsAttack() {
    return getType() == StatusType.PARALYSIS;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Status)) {
      return false;
    }
    final Status status = (Status) o;
    return getType() == status.getType();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getType());
  }

}
