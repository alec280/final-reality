package com.github.alec280.finalreality.model.spell;

import com.github.alec280.finalreality.model.status.Status;
import com.github.alec280.finalreality.model.status.StatusType;
import java.util.Objects;

/**
 * A class that holds all the information of a spell.
 *
 * @author Alexander Cuevas.
 */
public class Spell {

  private final String name;
  private final SpellType type;
  private final int manaCost;
  private final TargetType targetType;
  private final StatusType statusType;
  private final float statusChance;
  private final float healingPercent;

  /**
   * Creates a spell with a name, type, mana cost, target type and an associated status effect type.
   *
   * @see SpellType
   * @see TargetType
   * @see StatusType
   */
  public Spell(final String name, final SpellType type, final int manaCost, final TargetType targetType,
      final StatusType statusType, final float statusChance) {
    this.name = name;
    this.type = type;
    this.manaCost = manaCost;
    this.targetType = targetType;
    this.statusType = statusType;
    this.statusChance = statusChance;
    this.healingPercent = 0;
  }

  /**
   * Creates a spell with a name, type, mana cost, target type and healing percent.
   *
   * @see SpellType
   * @see TargetType
   */
  public Spell(final String name, final SpellType type, final int manaCost, final TargetType targetType,
      final float healingPercent) {
    this.name = name;
    this.type = type;
    this.manaCost = manaCost;
    this.targetType = targetType;
    this.statusType = StatusType.PARALYSIS;
    this.statusChance = 0;
    this.healingPercent = healingPercent;
  }

  public String getName() {
    return name;
  }

  public SpellType getType() {
    return type;
  }

  public int getManaCost() {
    return manaCost;
  }

  public TargetType getTargetType() {
    return targetType;
  }

  private StatusType getStatusType() {
    return statusType;
  }

  private Status getStatus(final int magicDamage) {
    return new Status(getStatusType(), magicDamage);
  }

  public float getStatusChance() {
    return statusChance;
  }

  public float getHealingPercent() {
    return healingPercent;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Spell)) {
      return false;
    }
    final Spell spell = (Spell) o;
    return getName().equals(spell.getName()) &&
      getStatusType() == spell.getStatusType();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getType());
  }

}
