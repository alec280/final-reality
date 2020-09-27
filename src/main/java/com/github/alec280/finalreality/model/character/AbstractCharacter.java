package com.github.alec280.finalreality.model.character;

import com.github.alec280.finalreality.model.character.player.CharacterClass;
import com.github.alec280.finalreality.model.weapon.Weapon;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

/**
 * An abstract class that holds the common behaviour of all the characters in the game.
 *
 * @author Ignacio Slater Muñoz.
 * @author <Your name>
 */
public abstract class AbstractCharacter implements ICharacter {

  protected final BlockingQueue<ICharacter> turnsQueue;
  private final CharacterClass characterClass;
  private ScheduledExecutorService scheduledExecutor;

  protected final String name;
  protected final int maxHealth;
  protected final int defense;
  protected int health;

  protected AbstractCharacter(@NotNull BlockingQueue<ICharacter> turnsQueue,
      CharacterClass characterClass, @NotNull String name, final int maxHealth, final int defense) {
    this.turnsQueue = turnsQueue;
    this.name = name;
    this.maxHealth = maxHealth;
    this.health = maxHealth;
    this.defense = defense;
    this.characterClass = characterClass;
  }

  @Override
  public void waitTurn() {
    scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    scheduledExecutor.schedule(this::addToQueue, getWeight() / 10, TimeUnit.SECONDS);
  }

  /**
   * Adds this character to the turns queue.
   */
  private void addToQueue() {
    turnsQueue.add(this);
    scheduledExecutor.shutdown();
  }

  @Override
  public String getName() { return name; }

  @Override
  public int getMaxHealth() { return maxHealth; }

  @Override
  public int getHealth() { return health; }

  @Override
  public int getDefense() { return defense; }

  @Override
  public int getDamage() { return 0; }

  @Override
  public int getWeight() { return 10; }

  @Override
  public boolean canEquip(Weapon weapon) { return false; }

  @Override
  public CharacterClass getCharacterClass() {
    return characterClass;
  }
}
