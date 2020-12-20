package com.github.cc3002.finalreality.model.character.player;

import com.github.alec280.finalreality.model.character.player.Thief;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Set of tests for the Thief class.
 *
 * @see Thief
 * @author Alexander Cuevas.
 */
public class ThiefTest extends AbstractPlayerTest {

  /**
   * Setup method.
   * Creates a new engineer and links it to an array of characters.
   */
  @BeforeEach
  void setUp() {
    playerSetUp();
    testCharacters.add(testThief);
  }

  /**
   * Checks that the class' constructor and equals method works properly.
   */
  @Test
  void constructorTest() {
    var expectedThief = new Thief(THIEF_NAME, HEALTH, DEFENSE, turns);

    checkConstruction(expectedThief, testThief,
      new Thief("Citrus", HEALTH, DEFENSE, turns),
      testKnight);

    assertEquals("thief", expectedThief.getSpriteName());
  }

  @Override
  protected void equipWeapon() {
    assertFalse(testThief.canEquip(testAxe));
    assertTrue(testThief.canEquip(testSword));
    assertFalse(testThief.canEquip(testKnife));
    assertTrue(testThief.canEquip(testBow));
    assertTrue(testThief.canEquip(testStaff));

    assertNull(testThief.getEquippedWeapon());
    testThief.equip(testBow);
    assertEquals(testBow, testThief.getEquippedWeapon());

    testThief.setHealth(0);
    unableToEquip(testThief);
  }

}
