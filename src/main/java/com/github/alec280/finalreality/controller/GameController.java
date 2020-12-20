package com.github.alec280.finalreality.controller;


import com.github.alec280.finalreality.controller.handlers.EnemyHandler;
import com.github.alec280.finalreality.controller.handlers.PlayerHandler;
import com.github.alec280.finalreality.controller.phases.*;
import com.github.alec280.finalreality.model.character.Enemy;
import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.character.player.*;
import com.github.alec280.finalreality.model.weapon.*;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
  private final PropertyChangeSupport propertyChange;
  private IPhase phase;

  private boolean playerTurn;
  private boolean battleStarted;
  private boolean emptyQueue;
  private int enemyIdx;
  private int playerIdx;
  private int weaponIdx;

  public final static String WHITESPACE = "          ";


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
    this.battleStarted = false;
    this.emptyQueue = true;
    this.enemyIdx = 0;
    this.playerIdx = 0;
    this.weaponIdx = 0;
    this.propertyChange = new PropertyChangeSupport(this);
    this.setPhase(new StartPhase());
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
   * Starts the timer for all characters.
   */
  public void startWait() {
    List<IPlayerCharacter> party = getUser().getParty();
    for (Enemy enemy : enemies) {
      enemy.waitTurn();
    }
    for (IPlayerCharacter character : party) {
      character.waitTurn();
    }
    try {
      phase.toIdlePhase();
    } catch (InvalidTransitionException e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks if the user lost the game whenever a player character dies.
   */
  public void onPlayerDeath() {
    boolean survivors = false;
    for (int i = 0; i < user.getParty().size(); i++) {
      IPlayerCharacter player = user.getParty().get(i);
      if (!player.isAlive()) {
        turnsQueue.remove(player);
        propertyChange.firePropertyChange("playerDeath", -1, i);
      } else {
        survivors = true;
      }
    }
    if (!survivors) {
      try {
        phase.toEndPhase();
      } catch (InvalidTransitionException e) {
        e.printStackTrace();
      }
      propertyChange.firePropertyChange("playerVictory", true, false);
    }
  }

  /**
   * Checks if the user won the game whenever a enemy dies.
   */
  public void onEnemyDeath() {
    boolean survivors = false;
    for (int i = 0; i < enemies.size(); i++) {
      Enemy enemy = enemies.get(i);
      if (!enemy.isAlive()) {
        turnsQueue.remove(enemy);
        setEnemyIdx(enemyIdx + 1);
        propertyChange.firePropertyChange("enemyDeath", -1, i);
      } else {
      survivors = true;
      }
    }
    if (!survivors) {
      try {
        phase.toEndPhase();
      } catch (InvalidTransitionException e) {
        e.printStackTrace();
      }
      propertyChange.firePropertyChange("playerVictory", false, true);
    }
  }

  /**
   * The character that ended its turn awaits its next turn.
   * Tries to start the turn of the next character, if there is one.
   */
  public void onTurnEnded() {
    ICharacter character = turnsQueue.poll();
    if (character == null || !phase.canContinue()) {
      return;
    }
    character.waitTurn();
    try {
      phase.toIdlePhase();
    } catch (InvalidTransitionException e) {
      e.printStackTrace();
    }
    if (!turnsQueue.isEmpty()) {
      turnsQueue.peek().startTurn();
    } else {
      emptyQueue = true;
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
    ICharacter character = turnsQueue.peek();
    if (playerTurn) {
      for (IPlayerCharacter player : getUser().getParty()) {
        if (player.equals(character)) {
          this.playerIdx = getUser().getParty().indexOf(player);
        }
      }
    }
    propertyChange.firePropertyChange("activeCharacter", null, character);
  }

  /**
   * Tries to start the turn of a character if the turnsQueue was empty and the game is running an actual battle.
   */
  public void tryToStartTurn() {
    ICharacter character = turnsQueue.peek();
    if (character != null && isBattleStarted() && emptyQueue) {
      character.startTurn();
      emptyQueue = false;
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
      propertyChange.firePropertyChange("weaponChanged", old_weapon, weapon);
    } else {
      propertyChange.firePropertyChange("unsuccessfulAction", false, true);
    }
  }

  /**
   * Makes a character do damage to another character, both must be alive.
   */
  public void performAttack(@NotNull ICharacter aggressor,@NotNull ICharacter defender) {
    aggressor.doDamage(defender);
    int damage = Math.max(1, aggressor.getAttack() - defender.getDefense());
    String action = aggressor.getName() + " attacks " + defender.getName() + "! It deals " + damage + " damage.";
    propertyChange.firePropertyChange("action", null, action);
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
   * Creates a set of four enemies with similar stats.
   */
  public void createCuboids() {
    createEnemy("Cuboid A", 20, 5, 10, 10);
    createEnemy("Cuboid B", 30, 0, 7, 10);
    createEnemy("Cuboid C", 15, 5, 10, 10);
    createEnemy("Cuboid D", 20, 7, 12, 20);
  }

  /**
   * Creates a set of four player characters of different classes.
   */
  public void createParty() {
    createKnight("Knight", 20, 7);
    createEngineer("Engineer", 20, 2);
    createThief("Thief", 15, 0);
    createWhiteMage("White Mage", 15, 5, 10);
  }

  /**
   * Creates a set of five weapons of different classes.
   */
  public void createWeapons() {
    createAxe("Axe", 15, 30);
    createBow("Bow", 20, 50);
    createKnife("Knife", 7, 10);
    createStaff("Staff", 5, 20, 15);
    createSword("Sword", 10, 20);
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
    int idx = 0;
    if (partySize > 0) {
      idx = rng.nextInt(partySize);
    }

    for (int i = 0; i < partySize; i++) {
      ICharacter player = party.get((idx + i) % partySize);
      if (player.isAlive()) {
        tryToPerformAttack(enemy, player);
        return;
      }
    }
  }

  /**
   * Adds a listener for this controller.
   */
  public void addListener(PropertyChangeListener listener) {
    propertyChange.addPropertyChangeListener(listener);
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

  /**
   * Returns the weapon at the given index.
   */
  public IWeapon getWeapon(int idx) {
    var inventory = user.getInventory();
    var keySet = inventory.keySet();
    for (IWeapon weapon : keySet) {
      idx -= inventory.get(weapon);
      if (idx < 0) {
        return weapon;
      }
    }
    return null;
  }

  /**
   * Returns the information of a character as a String.
   */
  public String getCharacterInfo(ICharacter character) {
    if (character == null) {
      return "???";
    }
    return character.getName() + "\nHealth = " + character.getHealth() + " / " +  character.getMaxHealth() +
        WHITESPACE + "Defense = " + character.getDefense() + WHITESPACE + "Attack = " + character.getAttack() +
        WHITESPACE + "Weight = " + character.getWeight();
  }

  /**
   * Returns the information of a weapon as a String.
   */
  public String getWeaponInfo(IWeapon weapon) {
    if (weapon == null) {
      return "???";
    }
    return weapon.getName() + "\nAttack = " + weapon.getAttack() + WHITESPACE + "Weight = " + weapon.getWeight();
  }

  /**
   * Returns the index of the currently selected enemy.
   */
  public int getEnemyIdx() {
    return enemyIdx;
  }

  /**
   * Returns the index of the currently selected weapon.
   */
  public int getWeaponIdx() {
    return weaponIdx;
  }

  /**
   * Returns the index of the current player.
   */
  public int getPlayerIdx() {
    return playerIdx;
  }

  /**
   * Set the index of the currently selected enemy.
   * The index can only be invalid if all enemies have been defeated.
   */
  public void setEnemyIdx(int idx) {
    int counter = enemies.size();
    if (idx > getEnemyIdx()) {
      if (idx >= enemies.size()) {
        idx = 0;
      }
      while (!enemies.get(idx).isAlive() && counter > 0) {
        idx = (idx + 1) % enemies.size();
        counter--;
      }
    } else {
      if (idx <= -1) {
        idx = enemies.size() - 1;
      }
      while (!enemies.get(idx).isAlive() && counter > 0) {
        idx--;
        if (idx == -1) {
          idx = enemies.size() - 1;
        }
        counter--;
      }
    }
    this.enemyIdx = idx;
  }

  /**
   * Set the index of the currently selected weapon.
   */
  public void setWeaponIdx(int idx) {
    if (idx > getWeaponIdx()) {
      if (idx >= user.getInventorySize()) {
        idx = 0;
      }
    } else if (idx <= -1) {
      idx = user.getInventorySize() - 1;
    }
    this.weaponIdx = idx;
  }

  /**
   * Returns true if a battle has started.
   */
  public boolean isBattleStarted() {
    return battleStarted;
  }

  /**
   * Sets if a battle has started or not.
   */
  public void setBattleStarted(boolean battleStarted) {
    this.battleStarted = battleStarted;
  }

  private void finishPlayerCreation(IPlayerCharacter character) {
    character.addListener(playerHandler);
    user.addPlayerCharacter(character);
  }

  private void finishWeaponCreation(IWeapon weapon) {
    user.addWeapon(weapon);
  }

}
