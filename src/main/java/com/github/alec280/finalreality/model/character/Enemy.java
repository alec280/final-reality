package com.github.alec280.finalreality.model.character;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import org.jetbrains.annotations.NotNull;

/**
 * A class that holds all the information of a single enemy of the game.
 *
 * @author Ignacio Slater Muñoz
 * @author Alexander Cuevas.
 */
public class Enemy extends AbstractCharacter {

  private final int attack;
  private final int weight;

  /**
   * Creates a new Enemy character.
   *
   * @param name
   *     the enemy's name
   * @param maxHealth
   *     the enemy's maximum health
   * @param defense
   *     the enemy's defense
   * @param attack
   *     the enemy's attack
   * @param weight
   *     the enemy's weight
   * @param turnsQueue
   *     the queue with the characters waiting for their turn
   */
  public Enemy(@NotNull final String name, final int maxHealth, final int defense, final int attack,
      final int weight, @NotNull final BlockingQueue<ICharacter> turnsQueue) {
    super(name, maxHealth, defense, turnsQueue);
    this.attack = attack;
    this.weight = weight;
  }

  @Override
  public int getAttack() {
    return attack;
  }

  @Override
  public int getWeight() {
    return weight;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Enemy)) {
      return false;
    }
    final Enemy enemy = (Enemy) o;
    return getName().equals(enemy.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getClass());
  }

}
