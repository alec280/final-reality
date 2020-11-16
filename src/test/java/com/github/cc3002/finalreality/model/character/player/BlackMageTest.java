package com.github.cc3002.finalreality.model.character.player;

import com.github.alec280.finalreality.model.character.player.BlackMage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Set of tests for the BlackMage class.
 *
 * @see BlackMage
 * @author Alexander Cuevas.
 */
public class BlackMageTest extends AbstractMageTest {

  /**
   * Setup method.
   * Creates a new black mage and links it to an array of characters.
   */
  @BeforeEach
  void setUp() {
    playerSetUp();
    testCharacters.add(testBlackMage);
  }

  /**
   * Checks that the class' constructor and equals method works properly.
   */
  @Test
  void constructorTest() {
    var expectedBlackMage = new BlackMage(BLACK_MAGE_NAME, HEALTH, DEFENSE, MANA, turns);

    checkConstruction(expectedBlackMage, testBlackMage,
      new BlackMage("Soci", HEALTH, DEFENSE, MANA, turns),
      testThief);
  }

  @Override
  protected void equipWeapon() {
    assertFalse(testBlackMage.canEquip(testAxe));
    assertFalse(testBlackMage.canEquip(testSword));
    assertTrue(testBlackMage.canEquip(testKnife));
    assertFalse(testBlackMage.canEquip(testBow));
    assertTrue(testBlackMage.canEquip(testStaff));

    assertNull(testBlackMage.getEquippedWeapon());
    testBlackMage.equip(testStaff);
    assertEquals(testStaff, testBlackMage.getEquippedWeapon());

    testBlackMage.setHealth(0);
    unableToEquip(testBlackMage);
  }

}
