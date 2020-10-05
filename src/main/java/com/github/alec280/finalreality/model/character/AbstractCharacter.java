package com.github.alec280.finalreality.model.character;

import com.github.alec280.finalreality.model.weapon.IWeapon;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

/**
 * An abstract class that holds the common behaviour of all the characters in the game.
 *
 * @author Ignacio Slater Muñoz
 * @author Alexander Cuevas.
 */
public abstract class AbstractCharacter implements ICharacter {

  protected final BlockingQueue<ICharacter> turnsQueue;
  private ScheduledExecutorService scheduledExecutor;

  protected final String name;
  protected final int maxHealth;
  protected final int defense;
  protected int health;

  protected AbstractCharacter(@NotNull String name, final int maxHealth, final int defense,
      @NotNull BlockingQueue<ICharacter> turnsQueue) {
    this.turnsQueue = turnsQueue;
    this.name = name;
    this.maxHealth = maxHealth;
    this.health = maxHealth;
    this.defense = defense;
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
  public String getName() {
    return name;
  }

  @Override
  public int getMaxHealth() {
    return maxHealth;
  }

  @Override
  public int getHealth() {
    return health;
  }

  @Override
  public int getDefense() {
    return defense;
  }

  @Override
  public int getDamage() {
    return 0;
  }

  @Override
  public int getWeight() {
    return 10;
  }

  @Override
  public boolean canEquip(IWeapon weapon) {
    return false;
  }

}
