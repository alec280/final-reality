package com.github.alec280.finalreality.controller;


import com.github.alec280.finalreality.controller.handlers.EnemyHandler;
import com.github.alec280.finalreality.controller.handlers.PlayerHandler;
import com.github.alec280.finalreality.controller.phases.IPhase;
import com.github.alec280.finalreality.controller.phases.IdlePhase;
import com.github.alec280.finalreality.controller.phases.InvalidActionException;
import com.github.alec280.finalreality.controller.phases.InvalidTransitionException;
import com.github.alec280.finalreality.model.character.Enemy;
import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.character.player.*;
import com.github.alec280.finalreality.model.weapon.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This represents a controller of the game, it handles the interaction between the user
 * of this game and the model of this game.
 *
 * @author Alexander Cuevas.
 */
public class GameController {
  private final User user;
  private final List<Enemy> enemies;
  private final BlockingQueue<ICharacter> turnsQueue;
  private final PlayerHandler playerHandler;
  private final EnemyHandler enemyHandler;
  private boolean playerTurn;
  private IPhase phase;

  /**
   * Creates a new GameController.
   */
  public GameController() {
    this.user = new User();
    this.enemies = new ArrayList<>();
    this.turnsQueue = new LinkedBlockingQueue<>(8);
    this.playerHandler = new PlayerHandler(this);
    this.enemyHandler = new EnemyHandler(this);
    this.playerTurn = false;
    this.setPhase(new IdlePhase());
  }

  /**
   * Returns the user of this controller.
   */
  public User getUser() {
    return user;
  }

  /**
   * Returns the turnsQueue of this controller.
   */
  public BlockingQueue<ICharacter> getTurnsQueue() {
    return turnsQueue;
  }

  /**
   * Returns the enemies tracked by this controller.
   */
  public List<Enemy> getEnemies() {
    return enemies;
  }

  /**
   * Returns the current phase.
   */
  public IPhase getPhase() {
    return phase;
  }

  /**
   * Returns true if the current character is a player character.
   */
  public boolean isPlayerTurn() {
    return playerTurn;
  }

  /**
   * Checks if the user lost the game whenever a player character dies.
   */
  public void onPlayerDeath() {
    for (var player : user.getParty()) {
      if (player.isAlive()) {
        return;
      }
    }
    // At this point, the controller knows that the user lost the game.
  }

  /**
   * Checks if the user won the game whenever a enemy dies.
   */
  public void onEnemyDeath() {
    for (var enemy : enemies) {
      if (enemy.isAlive()) {
        return;
      }
    }
    // At this point, the controller knows that the user won the game.
  }

  /**
   * Does something whenever a character ends its turn. The character that ended its turn awaits its next turn.
   * Tries to start the turn of the next character, if there is one.
   */
  public void onTurnEnded() {
    ICharacter character = turnsQueue.poll();
    if (character != null) {
      character.waitTurn();
    } else {
      return;
    }
    try {
      phase.toIdlePhase();
    } catch (InvalidTransitionException e) {
      e.printStackTrace();
    }
    if (!turnsQueue.isEmpty()) {
      turnsQueue.peek().startTurn();
    }
  }

  /**
   * Does something whenever a character starts its turn.
   */
  public void onTurnStarted(final boolean playerTurn) {
    this.playerTurn = playerTurn;
    try {
      phase.toSelectingAttackTargetPhase();
    } catch (InvalidTransitionException e) {
      e.printStackTrace();
    }
  }

  /**
   * Tries to equip a weapon to a player character while managing the user's inventory.
   */
  public void equipWeapon(@NotNull IWeapon weapon, @NotNull IPlayerCharacter player) {
    if (player.canEquip(weapon)) {
      IWeapon old_weapon = player.getEquippedWeapon();
      player.equip(weapon);
      user.removeWeapon(weapon);
      if (old_weapon != null) {
        user.addWeapon(old_weapon);
      }
    }
    // At this point, the controller should tell the user that the character can't be
    // equipped with the given weapon.
  }

  /**
   * Makes a character do damage to another character, both must be alive.
   */
  public void performAttack(@NotNull ICharacter aggressor,@NotNull ICharacter defender) {
    aggressor.doDamage(defender);
  }

  /**
   * Creates a new Enemy and keeps track of it by adding it to a list of enemies.
   */
  public void createEnemy(@NotNull final String name, final int maxHealth, final int defense,
      final int attack, final int weight) {
    Enemy enemy = new Enemy(name, maxHealth, defense, attack, weight, turnsQueue);
    enemy.addListener(enemyHandler);
    enemies.add(enemy);
  }

  /**
   * Creates a new Knight and adds it to the user's party.
   */
  public void createKnight(@NotNull final String name, final int maxHealth, final int defense) {
    finishPlayerCreation(new Knight(name, maxHealth, defense, turnsQueue));
  }

  /**
   * Creates a new Engineer and adds it to the user's party.
   */
  public void createEngineer(@NotNull final String name, final int maxHealth, final int defense) {
    finishPlayerCreation(new Engineer(name, maxHealth, defense, turnsQueue));
  }

  /**
   * Creates a new Thief and adds it to the user's party.
   */
  public void createThief(@NotNull final String name, final int maxHealth, final int defense) {
    finishPlayerCreation(new Thief(name, maxHealth, defense, turnsQueue));
  }

  /**
   * Creates a new BlackMage and adds it to the user's party.
   */
  public void createBlackMage(@NotNull final String name, final int maxHealth, final int defense,
      final int maxMana) {
    finishPlayerCreation(new BlackMage(name, maxHealth, defense, maxMana, turnsQueue));
  }

  /**
   * Creates a new WhiteMage and adds it to the user's party.
   */
  public void createWhiteMage(@NotNull final String name, final int maxHealth, final int defense,
      final int maxMana) {
    finishPlayerCreation(new WhiteMage(name, maxHealth, defense, maxMana, turnsQueue));
  }

  /**
   * Creates a new Axe and adds it to the user's inventory.
   */
  public void createAxe(final String name, final int attack, final int weight) {
    finishWeaponCreation(new Axe(name, attack, weight));
  }

  /**
   * Creates a new Bow and adds it to the user's inventory.
   */
  public void createBow(final String name, final int attack, final int weight) {
    finishWeaponCreation(new Bow(name, attack, weight));
  }

  /**
   * Creates a new Knife and adds it to the user's inventory.
   */
  public void createKnife(final String name, final int attack, final int weight) {
    finishWeaponCreation(new Knife(name, attack, weight));
  }

  /**
   * Creates a new Sword and adds it to the user's inventory.
   */
  public void createSword(final String name, final int attack, final int weight) {
    finishWeaponCreation(new Sword(name, attack, weight));
  }

  /**
   * Creates a new Staff and adds it to the user's inventory.
   */
  public void createStaff(final String name, final int attack, final int weight, final int magicAttack) {
    finishWeaponCreation(new Staff(name, attack, weight, magicAttack));
  }

  /**
   * Causes an enemy to try to attack a user's player character.
   */
  public void tryToEnemyAttack(@NotNull ICharacter enemy, @NotNull List<IPlayerCharacter> party) {
    Random rng = new Random();
    int partySize = party.size();
    int idx = rng.nextInt(partySize);

    for (int i = 0; i < partySize; i++) {
      ICharacter player = party.get((idx + i) % partySize);
      if (player.isAlive()) {
        tryToPerformAttack(enemy, player);
        break;
      }
    }
  }

  /**
   * Sets the given phase of the game flow as the current one.
   */
  public void setPhase(final @NotNull IPhase phase) {
    this.phase = phase;
    phase.setController(this);
  }

  /**
   * Tries to open the inventory, the current phase must allow it.
   */
  public void toggleInventory() {
    try {
      phase.toggleInventory();
    } catch (InvalidActionException e) {
      e.printStackTrace();
    }
  }

  /**
   * Tries to equip a weapon, the current phase must allow it.
   */
  public void tryToEquipWeapon(@NotNull IWeapon weapon, @NotNull IPlayerCharacter player) {
    try {
      phase.equipWeapon(weapon, player);
    } catch (InvalidActionException e) {
      e.printStackTrace();
    }
  }

  /**
   * Tries to perform an attack, the current phase must allow it.
   */
  public void tryToPerformAttack(@NotNull ICharacter aggressor,@NotNull ICharacter defender) {
    try {
      phase.performAttack(aggressor, defender);
    } catch (InvalidActionException e) {
      e.printStackTrace();
    }
  }

  private void finishPlayerCreation(IPlayerCharacter character) {
    character.addListener(playerHandler);
    user.addPlayerCharacter(character);
  }

  private void finishWeaponCreation(IWeapon weapon) {
    user.addWeapon(weapon);
  }
}
