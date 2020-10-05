package com.github.cc3002.finalreality.model.character.player;

import com.github.alec280.finalreality.model.character.player.Engineer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Set of tests for the Engineer class.
 *
 * @see Engineer
 * @author Alexander Cuevas.
 */
public class EngineerTest extends AbstractPlayerTest {

  /**
   * Setup method.
   * Creates a new engineer and links it to an array of characters.
   */
  @BeforeEach
  void setUp() {
    playerSetUp();
    testCharacters.add(testEngineer);
  }

  /**
   * Checks that the class' constructor and equals method works properly.
   */
  @Test
  void constructorTest() {
    var expectedEngineer = new Engineer(ENGINEER_NAME, HEALTH, DEFENSE, turns);

    checkConstruction(expectedEngineer, testEngineer,
      new Engineer("Sit", HEALTH, DEFENSE, turns),
      testThief);
  }

  @Override
  protected void equipWeapon() {
    assertTrue(testEngineer.canEquip(testAxe));
    assertFalse(testEngineer.canEquip(testSword));
    assertFalse(testEngineer.canEquip(testKnife));
    assertTrue(testEngineer.canEquip(testBow));
    assertFalse(testEngineer.canEquip(testStaff));

    assertNull(testEngineer.getEquippedWeapon());
    testEngineer.equip(testAxe);
    assertEquals(testAxe, testEngineer.getEquippedWeapon());
  }

}
