package com.github.alec280.finalreality.model.character.player;

import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.weapon.IWeapon;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;

/**
 * A class that holds all the information of a single knight.
 *
 * @author Alexander Cuevas.
 */
public class Knight extends AbstractPlayer {

  /**
   * Creates a new Knight player.
   *
   * @param name
   *     the knight's name
   * @param maxHealth
   *     the knight's maximum health
   * @param defense
   *     the knight's defense
   * @param turnsQueue
   *     the queue with the characters waiting for their turn
   */
  public Knight(@NotNull String name, int maxHealth, int defense,
      @NotNull BlockingQueue<ICharacter> turnsQueue) {
    super(name, maxHealth, defense, turnsQueue);
  }

  @Override
  public boolean canEquip(@NotNull IWeapon weapon) {
    return super.canEquip(weapon) && weapon.canBeEquippedToKnight();
  }

  @Override
  public String getSpriteName() {
    return "knight";
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Knight)) {
      return false;
    }
    final Knight knight = (Knight) o;
    return getName().equals(knight.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getClass());
  }

}
