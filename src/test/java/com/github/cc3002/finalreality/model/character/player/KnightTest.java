package com.github.cc3002.finalreality.model.character.player;

import com.github.alec280.finalreality.model.character.player.Knight;
import com.github.cc3002.finalreality.model.character.player.AbstractPlayerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Set of tests for the Knight class.
 *
 * @see Knight
 * @author Alexander Cuevas.
 */
public class KnightTest extends AbstractPlayerTest {

  /**
   * Setup method.
   * Creates a new knight and links it to an array of characters.
   */
  @BeforeEach
  void setUp() {
    playerSetUp();
    testCharacters.add(testKnight);
  }

  /**
   * Checks that the class' constructor and equals method works properly.
   */
  @Test
  void constructorTest() {
    var expectedKnight = new Knight(KNIGHT_NAME, HEALTH, DEFENSE, turns);

    checkConstruction(expectedKnight, testKnight,
      new Knight("Alfonso", HEALTH, DEFENSE, turns),
      testThief);

    assertEquals("knight", expectedKnight.getSpriteName());
  }

  @Override
  protected void equipWeapon() {
    assertTrue(testKnight.canEquip(testAxe));
    assertTrue(testKnight.canEquip(testSword));
    assertTrue(testKnight.canEquip(testKnife));
    assertFalse(testKnight.canEquip(testBow));
    assertFalse(testKnight.canEquip(testStaff));

    assertNull(testKnight.getEquippedWeapon());
    testKnight.equip(testAxe);
    assertEquals(testAxe, testKnight.getEquippedWeapon());

    testKnight.setHealth(0);
    unableToEquip(testKnight);
  }

}
