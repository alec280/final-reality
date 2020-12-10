package com.github.cc3002.finalreality.controller;

import com.github.alec280.finalreality.controller.GameController;
import com.github.alec280.finalreality.controller.phases.*;
import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.character.player.IPlayerCharacter;
import com.github.alec280.finalreality.model.weapon.Axe;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class containing the tests for all the phases.
 *
 * @author Alexander Cuevas.
 * @see IPhase
 */
public class PhaseTest {
  private final GameController controller = new GameController();
  private static final int TEST_VALUE = 10;

  /**
   * Checks that the transitions between phases work appropriately, and that the user
   * can perform the appropriate actions.
   */
  @Test
  void transitionTest() throws InterruptedException {
    final BlockingQueue<ICharacter> turnsQueue = controller.getTurnsQueue();
    final List<IPlayerCharacter> party = controller.getUser().getParty();
    final Axe testAxe = new Axe("Axe", TEST_VALUE, TEST_VALUE);

    checkConstruction(new IdlePhase(), new IdlePhase(), new ChangingWeaponPhase());
    checkConstruction(new ChangingWeaponPhase(), new ChangingWeaponPhase(), new SelectingAttackTargetPhase());
    checkConstruction(new SelectingAttackTargetPhase(), new SelectingAttackTargetPhase(), new IdlePhase());

    assertEquals(new IdlePhase(), controller.getPhase());

    assertThrows(InvalidTransitionException.class, controller.getPhase()::toChangingWeaponPhase);
    assertThrows(InvalidTransitionException.class, controller.getPhase()::toIdlePhase);
    assertThrows(InvalidActionException.class, controller.getPhase()::toggleInventory);

    controller.createThief("Morgana", TEST_VALUE, TEST_VALUE);
    controller.createAxe("Axe", TEST_VALUE, TEST_VALUE);
    party.get(0).waitTurn();

    Thread.sleep(2000);

    assertEquals(new IdlePhase(), controller.getPhase());

    assertThrows(InvalidActionException.class, () -> controller.getPhase().equipWeapon(testAxe, party.get(0)));
    assertThrows(InvalidActionException.class, () -> controller.getPhase().performAttack(party.get(0), party.get(0)));
    assertThrows(InvalidActionException.class, controller.getPhase()::toggleInventory);

    assert turnsQueue.peek() != null;
    turnsQueue.peek().startTurn();

    assertEquals(new SelectingAttackTargetPhase(), controller.getPhase());

    assertThrows(InvalidTransitionException.class, controller.getPhase()::toSelectingAttackTargetPhase);
    assertThrows(InvalidActionException.class, () -> controller.getPhase().equipWeapon(testAxe, party.get(0)));

    controller.toggleInventory();

    assertEquals(new ChangingWeaponPhase(), controller.getPhase());

    assertThrows(InvalidTransitionException.class, controller.getPhase()::toIdlePhase);
    assertThrows(InvalidTransitionException.class, controller.getPhase()::toChangingWeaponPhase);
    assertThrows(InvalidActionException.class, () -> controller.getPhase().performAttack(party.get(0), party.get(0)));

    controller.tryToEquipWeapon(testAxe, party.get(0));
    controller.toggleInventory();

    assertEquals(new SelectingAttackTargetPhase(), controller.getPhase());

    controller.tryToPerformAttack(party.get(0), party.get(0));

    assertEquals(new IdlePhase(), controller.getPhase());
  }

  protected void checkConstruction(final IPhase expectedPhase,
                                   final IPhase testEqualPhase,
                                   final IPhase sameClassDifferentPhase) {

    assertEquals(expectedPhase, expectedPhase);
    assertEquals(expectedPhase, testEqualPhase);
    assertNotEquals(sameClassDifferentPhase, testEqualPhase);
    assertEquals(expectedPhase.hashCode(), testEqualPhase.hashCode());
  }
}
