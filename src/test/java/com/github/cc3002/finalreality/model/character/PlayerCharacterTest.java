package com.github.cc3002.finalreality.model.character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.github.alec280.finalreality.model.character.Enemy;
import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.character.player.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Set of tests for the {@code GameCharacter} class.
 *
 * @author Ignacio Slater Muñoz
 * @author Alexander Cuevas.
 * @see ICharacter
 */
class PlayerCharacterTest extends AbstractCharacterTest {

  private static final String BLACK_MAGE_NAME = "Vivi";
  private static final String KNIGHT_NAME = "Adelbert";
  private static final String WHITE_MAGE_NAME = "Eiko";
  private static final String ENGINEER_NAME = "Cid";
  private static final String THIEF_NAME = "Zidane";

  private BlackMage testBlackMage;
  private Knight testKnight;
  private WhiteMage testWhiteMage;
  private Engineer testEngineer;
  private Thief testThief;

  /**
   * Setup method.
   * Creates a new character named Vivi with 10 speed and links it to a turn queue.
   */
  @BeforeEach
  void setUp() {
    super.basicSetUp();
    testBlackMage = new BlackMage(BLACK_MAGE_NAME, 5, 3, 3, turns);
    testKnight = new Knight(KNIGHT_NAME, 5, 3, turns);
    testWhiteMage = new WhiteMage(WHITE_MAGE_NAME, 5, 3, 3, turns);
    testEngineer = new Engineer(ENGINEER_NAME, 5, 3, turns);
    testThief = new Thief(THIEF_NAME, 5, 3, turns);

    testCharacters.add(testKnight);
  }

  /**
   * Checks that the class' constructor and equals method works properly.
   */
  @Test
  void constructorTest() {
    var expectedBlackMage = new BlackMage(BLACK_MAGE_NAME, 5, 3, 3, turns);
    var expectedKnight = new Knight(KNIGHT_NAME, 5, 3, turns);
    var expectedWhiteMage = new WhiteMage(WHITE_MAGE_NAME, 5, 3, 3, turns);
    var expectedEngineer = new Engineer(ENGINEER_NAME, 5, 3, turns);
    var expectedThief = new Thief(THIEF_NAME, 5, 3, turns);

    checkConstruction(expectedBlackMage, testBlackMage,
      new BlackMage("Test", 5, 3, turns),
      new Knight(BLACK_MAGE_NAME, 5, 3, turns));

    checkConstruction(expectedKnight, testKnight,
      new Knight("Test", 5, 3, turns),
      new WhiteMage(KNIGHT_NAME, 5, 3, turns));

    checkConstruction(expectedWhiteMage, testWhiteMage,
      new WhiteMage("Test", 5, 3, turns),
      new Engineer(WHITE_MAGE_NAME, 5, 3, turns));

    checkConstruction(expectedEngineer, testEngineer,
      new Engineer("Test", 5, 3, turns),
      new Thief(ENGINEER_NAME, 5, 3, turns));

    checkConstruction(expectedThief, testThief,
      new Thief("Test", 5, 3, turns),
      new BlackMage(THIEF_NAME, 5, 3, turns));

  }

  @Test
  void equipWeaponTest() {
    for (var character : testCharacters) {
      if (character.canEquip(testWeapon)) {
        final Knight player = (Knight) character;
        assertNull(player.getEquippedWeapon());
        player.equip(testWeapon);
        assertEquals(testWeapon, player.getEquippedWeapon());
      }
    }
  }
}
