package com.github.cc3002.finalreality.model.character;

import com.github.alec280.finalreality.model.character.Enemy;
import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.weapon.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Abstract class containing the common tests for all the types of characters.
 *
 * @author Ignacio Slater Muñoz
 * @author Alexander Cuevas.
 * @see ICharacter
 */
public abstract class AbstractCharacterTest {

  protected BlockingQueue<ICharacter> turns;
  protected List<ICharacter> testCharacters;
  protected static final int HEALTH = 10;
  protected static final int DEFENSE = 3;
  protected static final int DEFAULT_ATTACK = 1;
  protected static final int DEFAULT_WEIGHT = 10;

  protected List<IWeapon> testWeapons;
  protected static final int WEAPON_DAMAGE = 10;
  protected static final int WEAPON_WEIGHT = 20;
  protected static final int WEAPON_MAGIC_DAMAGE = 20;

  protected Axe testAxe;
  protected Staff testStaff;
  protected Sword testSword;
  protected Bow testBow;
  protected Knife testKnife;

  /**
   * Checks that the character waits the appropriate amount of time for it's turn.
   */
  @Test
  void waitTurnTest() {
    assertTrue(turns.isEmpty());
    testCharacters.get(0).waitTurn();
    try {
      // Thread.sleep is not accurate so this values may be changed to adjust the
      // acceptable error margin.
      // We're testing that the character waits approximately 1 second.
      Thread.sleep(800);
      assertEquals(0, turns.size());
      Thread.sleep(400);
      assertEquals(1, turns.size());
      assertEquals(testCharacters.get(0), turns.peek());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Checks that two characters can fight correctly.
   */
  @Test
  void combatTest() {
    final ICharacter character = testCharacters.get(0);
    final ICharacter weakling = new Enemy("Weakling", HEALTH, 0,
      DEFAULT_ATTACK, DEFAULT_WEIGHT, turns);
    final ICharacter bandit = new Enemy("Bandit", HEALTH, 0,
      HEALTH, DEFAULT_WEIGHT, turns);
    final ICharacter champion = new Enemy("Champion", HEALTH, 0,
      HEALTH + DEFENSE, DEFAULT_WEIGHT, turns);

    minimumDamageCombat(character, weakling);
    fairCombat(character, bandit);
    unfairCombat(character, champion);
  }

  protected void fairCombat(final ICharacter first, final ICharacter second) {
    simulateCombat(first, second);
    assertFalse(first.isAlive());
    assertTrue(second.isAlive());
    assertTrue(second.getMaxHealth() > second.getHealth());
    undoDamage(first, second);
  }

  protected void unfairCombat(final ICharacter first, final ICharacter second) {
    simulateCombat(first, second);
    assertFalse(first.isAlive());
    assertTrue(second.isAlive());
    assertEquals(second.getMaxHealth() - DEFAULT_ATTACK, second.getHealth());
    undoDamage(first, second);
  }

  protected void minimumDamageCombat(final ICharacter first, final ICharacter second) {
    simulateCombat(first, second);
    assertFalse(second.isAlive());
    assertTrue(first.isAlive());
    assertEquals(1, first.getHealth());
    undoDamage(first, second);
  }

  protected void simulateCombat(final ICharacter first, final ICharacter second) {
    while (first.isAlive() && second.isAlive()) {
      first.doDamage(second);
      if (second.isAlive()) {
        second.doDamage(first);
      }
    }
  }

  protected void undoDamage(final ICharacter first, final ICharacter second) {
    first.setHealth(first.getMaxHealth());
    second.setHealth(second.getMaxHealth());
  }

  protected void checkConstruction(final ICharacter expectedCharacter,
      final ICharacter testEqualCharacter,
      final ICharacter sameClassDifferentCharacter,
      final ICharacter differentClassCharacter) {

    checkStats(expectedCharacter);

    assertEquals(expectedCharacter, expectedCharacter);
    assertEquals(expectedCharacter, testEqualCharacter);
    assertNotEquals(sameClassDifferentCharacter, testEqualCharacter);
    assertNotEquals(testEqualCharacter, differentClassCharacter);
    assertEquals(expectedCharacter.hashCode(), testEqualCharacter.hashCode());
  }

  protected void basicSetUp() {
    turns = new LinkedBlockingQueue<>();
    testCharacters = new ArrayList<>();
    testWeapons = new ArrayList<>();

    testAxe = new Axe("Axe", WEAPON_DAMAGE, WEAPON_WEIGHT);
    testStaff = new Staff("Staff", WEAPON_DAMAGE, WEAPON_WEIGHT, WEAPON_MAGIC_DAMAGE);
    testSword = new Sword("Sword", WEAPON_DAMAGE, WEAPON_WEIGHT);
    testBow = new Bow("Bow", WEAPON_DAMAGE, WEAPON_WEIGHT);
    testKnife = new Knife("Knife", WEAPON_DAMAGE, WEAPON_WEIGHT);

    testWeapons.add(testAxe);
    testWeapons.add(testStaff);
    testWeapons.add(testSword);
    testWeapons.add(testBow);
    testWeapons.add(testKnife);
  }

  protected void checkStats(ICharacter character) {
    assertEquals(HEALTH, character.getHealth());
    assertEquals(HEALTH, character.getMaxHealth());
    assertEquals(DEFENSE, character.getDefense());
  }

}
