package com.github.cc3002.finalreality.model.character.player;

import com.github.alec280.finalreality.model.character.player.WhiteMage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Set of tests for the WhiteMage class.
 *
 * @see WhiteMage
 * @author Alexander Cuevas.
 */
public class WhiteMageTest extends AbstractMageTest {

  /**
   * Setup method.
   * Creates a new white mage and links it to an array of characters.
   */
  @BeforeEach
  void setUp() {
    playerSetUp();
    testCharacters.add(testWhiteMage);
  }

  /**
   * Checks that the class' constructor and equals method works properly.
   */
  @Test
  void constructorTest() {
    var expectedWhiteMage = new WhiteMage(WHITE_MAGE_NAME, HEALTH, DEFENSE, MANA, turns);

    checkConstruction(expectedWhiteMage, testWhiteMage,
      new WhiteMage("Iki", HEALTH, DEFENSE, MANA, turns),
      testThief);

    assertEquals("white_mage", expectedWhiteMage.getSpriteName());
    assertEquals(MANA, expectedWhiteMage.getMana());
    assertEquals(MANA, expectedWhiteMage.getMaxMana());
  }

  @Override
  protected void equipWeapon() {
    assertFalse(testWhiteMage.canEquip(testAxe));
    assertFalse(testWhiteMage.canEquip(testSword));
    assertFalse(testWhiteMage.canEquip(testKnife));
    assertFalse(testWhiteMage.canEquip(testBow));
    assertTrue(testWhiteMage.canEquip(testStaff));

    assertNull(testWhiteMage.getEquippedWeapon());
    testWhiteMage.equip(testStaff);
    assertEquals(testStaff, testWhiteMage.getEquippedWeapon());

    testWhiteMage.setHealth(0);
    unableToEquip(testWhiteMage);
  }

}
