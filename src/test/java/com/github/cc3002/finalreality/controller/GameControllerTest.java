package com.github.cc3002.finalreality.controller;

import com.github.alec280.finalreality.controller.GameController;
import com.github.alec280.finalreality.controller.phases.IdlePhase;
import com.github.alec280.finalreality.controller.phases.SelectingAttackTargetPhase;
import com.github.alec280.finalreality.model.character.Enemy;
import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.character.player.*;
import com.github.alec280.finalreality.model.weapon.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class containing the tests for the GameController class.
 *
 * @author Alexander Cuevas.
 * @see GameController
 */
public class GameControllerTest {
  private final GameController controller = new GameController();
  private static final int TEST_VALUE = 10;

  /**
   * Checks that the controller can create characters and weapons.
   */
  @Test
  void creatorTest() {
    final List<IPlayerCharacter> party = controller.getUser().getParty();
    final Map<IWeapon, Integer> inventory = controller.getUser().getInventory();
    final List<Enemy> enemies = controller.getEnemies();
    final BlockingQueue<ICharacter> turnsQueue = controller.getTurnsQueue();

    assertTrue(enemies.isEmpty());
    controller.createEnemy("Nemesis", TEST_VALUE, TEST_VALUE, TEST_VALUE, TEST_VALUE);
    assertEquals(enemies.get(0), new Enemy("Nemesis", TEST_VALUE, TEST_VALUE, TEST_VALUE,
        TEST_VALUE, turnsQueue));

    createPlayerCharacters(party, turnsQueue);
    createWeapons(inventory);
  }

  /**
   * Checks that the controller can equip weapons to characters while tracking its user's inventory.
   */
  @Test
  void equipTest() {
    final List<IPlayerCharacter> party = controller.getUser().getParty();
    final Map<IWeapon, Integer> inventory = controller.getUser().getInventory();

    final Axe testAxe = new Axe("Axe", TEST_VALUE, TEST_VALUE);
    final Sword testSword = new Sword("Sword", TEST_VALUE, TEST_VALUE);

    controller.createKnight("Arthur", TEST_VALUE, TEST_VALUE);
    controller.createAxe("Axe", TEST_VALUE, TEST_VALUE);
    controller.createSword("Sword", TEST_VALUE, TEST_VALUE);

    assertTrue(inventory.containsKey(testAxe));
    assertTrue(inventory.containsKey(testSword));
    assertNull(party.get(0).getEquippedWeapon());

    controller.equipWeapon(testAxe, party.get(0));

    assertFalse(inventory.containsKey(testAxe));
    assertEquals(party.get(0).getEquippedWeapon(), testAxe);

    controller.equipWeapon(testSword, party.get(0));

    assertFalse(inventory.containsKey(testSword));
    assertTrue(inventory.containsKey(testAxe));
    assertEquals(party.get(0).getEquippedWeapon(), testSword);

    party.get(0).setHealth(0);
    controller.equipWeapon(testAxe, party.get(0));

    assertFalse(inventory.containsKey(testSword));
    assertTrue(inventory.containsKey(testAxe));
    assertEquals(party.get(0).getEquippedWeapon(), testSword);

    controller.createSword("Sword", TEST_VALUE, TEST_VALUE);
    party.get(0).setHealth(1);
    controller.equipWeapon(testAxe, party.get(0));

    assertEquals(2, (int) inventory.get(testSword));
    controller.equipWeapon(testSword, party.get(0));
    assertEquals(1, (int) inventory.get(testSword));
  }

  /**
   * Checks that the controller can command characters to attack one another.
   */
  @Test
  void attackTest() {
    final List<IPlayerCharacter> party = controller.getUser().getParty();
    final List<Enemy> enemies = controller.getEnemies();

    controller.createKnight("Galahad", TEST_VALUE, TEST_VALUE);
    controller.createEnemy("Mordred", 2, TEST_VALUE, TEST_VALUE, TEST_VALUE);

    controller.performAttack(enemies.get(0), party.get(0));
    assertTrue(party.get(0).isAlive());

    controller.performAttack(party.get(0), enemies.get(0));
    controller.performAttack(party.get(0), enemies.get(0));
    assertFalse(enemies.get(0).isAlive());
  }

  /**
   * Checks that the characters are added and removed from the queue appropriately.
   */
  @Test
  void queueTest() throws InterruptedException {
    final BlockingQueue<ICharacter> turnsQueue = controller.getTurnsQueue();
    final List<IPlayerCharacter> party = controller.getUser().getParty();
    final List<Enemy> enemies = controller.getEnemies();

    assertTrue(turnsQueue.isEmpty());
    assertEquals(new IdlePhase(), controller.getPhase());

    for (int i = 0; i < 4; i++) {
      controller.createKnife("Knife", TEST_VALUE, TEST_VALUE);
      controller.createThief(Integer.toString(i), TEST_VALUE, TEST_VALUE);
      controller.createEnemy(Integer.toString(i), TEST_VALUE, TEST_VALUE, TEST_VALUE, TEST_VALUE);
      controller.equipWeapon(new Knife("Knife", TEST_VALUE, TEST_VALUE), party.get(i));
      party.get(i).waitTurn();
      enemies.get(i).waitTurn();
    }

    Thread.sleep(2000);
    assertEquals(8, turnsQueue.size());
    assertNotNull(turnsQueue.peek());
    turnsQueue.peek().startTurn();
    while (!turnsQueue.isEmpty()) {
      assertEquals(new SelectingAttackTargetPhase(), controller.getPhase());
      ICharacter character = turnsQueue.peek();
      assertNotNull(character);
      if (controller.isPlayerTurn()) {
        controller.tryToPerformAttack(character, character);
      } else {
        controller.tryToEnemyAttack(character, party);
      }
    }

    for (var player : party) {
      assertTrue(player.getHealth() < player.getMaxHealth());
    }
    for (var enemy : enemies) {
      assertEquals(enemy.getMaxHealth(), enemy.getHealth());
    }
  }

  private void createPlayerCharacters(final List<IPlayerCharacter> party, BlockingQueue<ICharacter> turnsQueue) {
    assertTrue(party.isEmpty());

    controller.createKnight("Lancelot", TEST_VALUE, TEST_VALUE);
    assertEquals(party.get(0), new Knight("Lancelot", TEST_VALUE, TEST_VALUE, turnsQueue));

    controller.createEngineer("Eiffel", TEST_VALUE, TEST_VALUE);
    assertEquals(party.get(1), new Engineer("Eiffel", TEST_VALUE, TEST_VALUE, turnsQueue));

    controller.createThief("Clyde", TEST_VALUE, TEST_VALUE);
    assertEquals(party.get(2), new Thief("Clyde", TEST_VALUE, TEST_VALUE, turnsQueue));

    controller.createWhiteMage("Francisco", TEST_VALUE, TEST_VALUE, TEST_VALUE);
    assertEquals(party.get(3), new WhiteMage("Francisco", TEST_VALUE, TEST_VALUE, TEST_VALUE, turnsQueue));

    controller.createBlackMage("Cromwell", TEST_VALUE, TEST_VALUE, TEST_VALUE);
    assertEquals(party.get(4), new BlackMage("Cromwell", TEST_VALUE, TEST_VALUE, TEST_VALUE, turnsQueue));
  }

  private void createWeapons(final Map<IWeapon, Integer> inventory) {
    final Staff testStaff = new Staff("Staff", TEST_VALUE, TEST_VALUE, TEST_VALUE);
    assertTrue(inventory.isEmpty());

    controller.createAxe("Axe", TEST_VALUE, TEST_VALUE);
    assertTrue(inventory.containsKey(new Axe("Axe", TEST_VALUE, TEST_VALUE)));

    controller.createSword("Sword", TEST_VALUE, TEST_VALUE);
    assertTrue(inventory.containsKey(new Sword("Sword", TEST_VALUE, TEST_VALUE)));

    controller.createKnife("Knife", TEST_VALUE, TEST_VALUE);
    assertTrue(inventory.containsKey(new Knife("Knife", TEST_VALUE, TEST_VALUE)));

    controller.createBow("Bow", TEST_VALUE, TEST_VALUE);
    assertTrue(inventory.containsKey(new Bow("Bow", TEST_VALUE, TEST_VALUE)));

    controller.createStaff("Staff", TEST_VALUE, TEST_VALUE, TEST_VALUE);
    assertTrue(inventory.containsKey(testStaff));

    assertEquals(1, (int) inventory.get(testStaff));
    controller.createStaff("Staff", TEST_VALUE, TEST_VALUE, TEST_VALUE);
    assertEquals(2, (int) inventory.get(testStaff));
  }

}
