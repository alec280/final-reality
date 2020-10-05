package com.github.cc3002.finalreality.model.character;

import com.github.alec280.finalreality.model.character.Enemy;
import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.character.player.Thief;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 * Set of tests for the Enemy class.
 *
 * @see Enemy
 * @author Alexander Cuevas.
 */
public class EnemyTest extends AbstractCharacterTest {

  private static final String ENEMY_NAME = "Goblin";
  private static final int DAMAGE = 3;
  private static final int WEIGHT = 10;

  private Enemy testEnemy;

  /**
   * Setup method.
   * Creates a new enemy named Goblin with 10 speed and links it to a turn queue.
   */
  @BeforeEach
  void setUp() {
    basicSetUp();
    testEnemy = new Enemy(ENEMY_NAME, HEALTH, DEFENSE, DAMAGE, WEIGHT, turns);
    testCharacters.add(testEnemy);
  }

  /**
   * Checks that the class' constructor and equals method works properly.
   */
  @Test
  void constructorTest() {
    var expectedEnemy = new Enemy(ENEMY_NAME, HEALTH, DEFENSE, DAMAGE, WEIGHT, turns);

    assertEquals(DAMAGE, expectedEnemy.getDamage());
    assertEquals(WEIGHT, expectedEnemy.getWeight());

    for (var weapon : testWeapons) {
      assertFalse(expectedEnemy.canEquip(weapon));
    }

    checkConstruction(expectedEnemy, testEnemy,
        new Enemy("Hobgoblin", HEALTH, DEFENSE, DAMAGE, WEIGHT, turns),
        new Thief(ENEMY_NAME, HEALTH, DEFENSE, turns));
  }
}