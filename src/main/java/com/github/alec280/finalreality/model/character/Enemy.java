package com.github.alec280.finalreality.model.character;

import com.github.alec280.finalreality.model.character.player.CharacterClass;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import org.jetbrains.annotations.NotNull;

/**
 * A class that holds all the information of a single enemy of the game.
 *
 * @author Ignacio Slater Muñoz
 * @author <Your name>
 */
public class Enemy extends AbstractCharacter {

  private final int damage;
  private final int weight;

  /**
   * Creates a new enemy with a name, damage, weight and the queue with the
   * characters ready to play.
   */
  public Enemy(@NotNull final String name, final int maxHealth, final int defense, final int damage,
      final int weight, @NotNull final BlockingQueue<ICharacter> turnsQueue) {
    super(turnsQueue, CharacterClass.ENEMY, name, maxHealth, defense);
    this.damage = damage;
    this.weight = weight;
  }

  @Override
  public int getDamage() {
    return damage;
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
    return getDamage() == enemy.getDamage() &&
      getWeight() == enemy.getWeight();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getDamage(), getWeight());
  }
}
