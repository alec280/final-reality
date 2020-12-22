package com.github.alec280.finalreality.model.character;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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

  private final PropertyChangeSupport propertyChange;
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
    this.propertyChange = new PropertyChangeSupport(this);
  }

  /**
   * Adds this character to the turns queue.
   */
  private void addToQueue() {
    scheduledExecutor.shutdown();
    if (isAlive()) {
      turnsQueue.add(this);
      propertyChange.firePropertyChange("ready", false, true);
    }
  }

  @Override
  public void addListener(PropertyChangeListener listener) {
    propertyChange.addPropertyChangeListener(listener);
  }

  @Override
  public void waitTurn() {
    scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    scheduledExecutor.schedule(this::addToQueue, getWeight() / 10, TimeUnit.SECONDS);
  }

  @Override
  public void startTurn() {
    propertyChange.firePropertyChange("turn", false, true);
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
  public void setHealth(final int value) {
    final int oldHealth = health;
    health = Math.max(0, Math.min(getMaxHealth(), value));
    propertyChange.firePropertyChange("health", oldHealth, health);
  }

  @Override
  public int getDefense() {
    return defense;
  }

  @Override
  public int getAttack() {
    return 1;
  }

  @Override
  public int getWeight() {
    return 10;
  }

  @Override
  public boolean isAlive() {
    return getHealth() > 0;
  }

  @Override
  public void doDamage(@NotNull ICharacter target) {
    final int damage = Math.max(1, getAttack() - target.getDefense());
    target.setHealth(target.getHealth() - damage);
    propertyChange.firePropertyChange("turn", true, false);
  }

}
