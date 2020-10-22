package com.github.cc3002.finalreality.model.character.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.character.player.*;

import com.github.cc3002.finalreality.model.character.AbstractCharacterTest;
import org.junit.jupiter.api.Test;

/**
 * Abstract class containing the common tests for all the types of players.
 *
 * @author Alexander Cuevas.
 */
abstract class AbstractPlayerTest extends AbstractCharacterTest {

  protected static final String BLACK_MAGE_NAME = "Vivi";
  protected static final String KNIGHT_NAME = "Adelbert";
  protected static final String WHITE_MAGE_NAME = "Eiko";
  protected static final String ENGINEER_NAME = "Cid";
  protected static final String THIEF_NAME = "Zidane";

  protected BlackMage testBlackMage;
  protected Knight testKnight;
  protected WhiteMage testWhiteMage;
  protected Engineer testEngineer;
  protected Thief testThief;

  protected void playerSetUp() {
    super.basicSetUp();

    testBlackMage = new BlackMage(BLACK_MAGE_NAME, HEALTH, DEFENSE, 3, turns);
    testKnight = new Knight(KNIGHT_NAME, HEALTH, DEFENSE, turns);
    testWhiteMage = new WhiteMage(WHITE_MAGE_NAME, HEALTH, DEFENSE, 3, turns);
    testEngineer = new Engineer(ENGINEER_NAME, HEALTH, DEFENSE, turns);
    testThief = new Thief(THIEF_NAME, HEALTH, DEFENSE, turns);
  }

  /**
   * Checks that the player can handle weapons appropriately.
   */
  @Test
  void weaponTest() {
    final ICharacter character = testCharacters.get(0);

    hasNoWeapon(character);
    equipWeapon();
    hasWeapon(character);
    character.setHealth(0);
    for (var weapon : testWeapons) {
      assertFalse(character.canEquip(weapon));
    }
  }

  private void hasNoWeapon(ICharacter character) {
    assertEquals(DEFAULT_ATTACK, character.getAttack());
    assertEquals(DEFAULT_WEIGHT, character.getWeight());
  }

  private void hasWeapon(ICharacter character) {
    assertEquals(WEAPON_DAMAGE, character.getAttack());
    assertEquals(WEAPON_WEIGHT, character.getWeight());
  }

  abstract void equipWeapon();
}
