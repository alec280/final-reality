package com.github.cc3002.finalreality.model.status;

import com.github.alec280.finalreality.model.status.Status;
import com.github.alec280.finalreality.model.status.StatusType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class containing the tests for all the status effects.
 *
 * @see Status
 * @author Alexander Cuevas.
 */
class StatusTest {

  protected List<Status> testStatusEffects;
  private static final int DAMAGE = 20;

  private Status testParalysis;
  private Status testPoison;
  private Status testBurn;

  /**
   * Setup method.
   * Creates one status of every type and adds them to an array.
   */
  @BeforeEach
  void setUp() {
    testParalysis = new Status(StatusType.PARALYSIS, DAMAGE);
    testPoison = new Status(StatusType.POISON, DAMAGE);
    testBurn = new Status(StatusType.BURN, DAMAGE);

    testStatusEffects = new ArrayList<>();
    testStatusEffects.add(testParalysis);
    testStatusEffects.add(testPoison);
    testStatusEffects.add(testBurn);
  }

  /**
   * Checks that the class' constructor and equals method works properly.
   */
  @Test
  void constructorTest() {
    var expectedParalysis = new Status(StatusType.PARALYSIS, DAMAGE);
    var expectedPoison = new Status(StatusType.POISON, DAMAGE);
    var expectedBurn = new Status(StatusType.BURN, DAMAGE);

    assertTrue(testParalysis.preventsAttack());
    assertFalse(testPoison.preventsAttack());
    assertFalse(testBurn.preventsAttack());

    assertTrue(testParalysis.isOneShot());
    assertFalse(testPoison.isOneShot());
    assertFalse(testBurn.isOneShot());

    for (var status : testStatusEffects) {
      assertTrue(status.getDamage() < DAMAGE);
    }

    checkConstruction(expectedParalysis, testParalysis, testPoison);
    checkConstruction(expectedPoison, testPoison, testBurn);
    checkConstruction(expectedBurn, testBurn, testParalysis);
  }

  protected void checkConstruction(final Status expectedStatus,
      final Status testEqualStatus,
      final Status sameClassDifferentStatus) {
    assertEquals(expectedStatus, expectedStatus);
    assertEquals(expectedStatus, testEqualStatus);
    assertNotEquals(sameClassDifferentStatus, testEqualStatus);
    assertEquals(expectedStatus.hashCode(), testEqualStatus.hashCode());
  }

}
