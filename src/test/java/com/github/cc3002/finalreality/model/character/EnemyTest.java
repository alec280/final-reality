package com.github.cc3002.finalreality.model.character;

import com.github.alec280.finalreality.model.character.Enemy;
import com.github.alec280.finalreality.model.character.player.Thief;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Set of tests for the Enemy class.
 *
 * @see Enemy
 * @author Alexander Cuevas.
 */
public class EnemyTest extends AbstractCharacterTest {

  private static final String ENEMY_NAME = "Goblin";
  private Enemy testEnemy;

  /**
   * Setup method.
   * Creates a new enemy named Goblin with 10 speed and links it to a turn queue.
   */
  @BeforeEach
  void setUp() {
    basicSetUp();
    testEnemy = new Enemy(ENEMY_NAME, HEALTH, DEFENSE, DEFAULT_ATTACK, DEFAULT_WEIGHT, turns);
    testCharacters.add(testEnemy);
  }

  /**
   * Checks that the class' constructor and equals method works properly.
   */
  @Test
  void constructorTest() {
    var expectedEnemy = new Enemy(ENEMY_NAME, HEALTH, DEFENSE, DEFAULT_ATTACK,
      DEFAULT_WEIGHT, turns);

    assertEquals(DEFAULT_ATTACK, expectedEnemy.getAttack());
    assertEquals(DEFAULT_WEIGHT, expectedEnemy.getWeight());

    checkConstruction(expectedEnemy, testEnemy,
        new Enemy("Hobgoblin", HEALTH, DEFENSE, DEFAULT_ATTACK, DEFAULT_WEIGHT, turns),
        new Thief(ENEMY_NAME, HEALTH, DEFENSE, turns));
  }
}