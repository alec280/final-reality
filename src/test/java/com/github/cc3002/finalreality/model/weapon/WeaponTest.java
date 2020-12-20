package com.github.cc3002.finalreality.model.weapon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.github.alec280.finalreality.model.weapon.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class containing the tests for all the weapons.
 *
 * @see IWeapon
 * @author Alexander Cuevas.
 */
class WeaponTest {

  private static final String AXE_NAME = "Test Axe";
  private static final String STAFF_NAME = "Test Staff";
  private static final String SWORD_NAME = "Test Sword";
  private static final String BOW_NAME = "Test Bow";
  private static final String KNIFE_NAME = "Test Knife";
  private static final int DAMAGE = 15;
  private static final int SPEED = 10;
  private static final int MAGIC_DAMAGE = 20;

  private Axe testAxe;
  private Staff testStaff;
  private Sword testSword;
  private Bow testBow;
  private Knife testKnife;

  /**
   * Setup method.
   * Creates one weapon of every type.
   */
  @BeforeEach
  void setUp() {
    testAxe = new Axe(AXE_NAME, DAMAGE, SPEED);
    testStaff = new Staff(STAFF_NAME, DAMAGE, SPEED, MAGIC_DAMAGE);
    testSword = new Sword(SWORD_NAME, DAMAGE, SPEED);
    testBow = new Bow(BOW_NAME, DAMAGE, SPEED);
    testKnife = new Knife(KNIFE_NAME, DAMAGE, SPEED);
  }

  /**
   * Checks that the class' constructor and equals method works properly.
   */
  @Test
  void constructorTest() {
    var expectedAxe = new Axe(AXE_NAME, DAMAGE, SPEED);
    var expectedStaff = new Staff(STAFF_NAME, DAMAGE, SPEED, MAGIC_DAMAGE);
    var expectedSword = new Sword(SWORD_NAME, DAMAGE, SPEED);
    var expectedBow = new Bow(BOW_NAME, DAMAGE, SPEED);
    var expectedKnife = new Knife(KNIFE_NAME, DAMAGE, SPEED);

    assertEquals(DAMAGE, testAxe.getAttack());
    assertEquals(MAGIC_DAMAGE, testStaff.getMagicAttack());
    assertNotEquals(DAMAGE, testSword.getMagicAttack());
    assertEquals(SPEED, testBow.getWeight());
    assertEquals(KNIFE_NAME, testKnife.getName());

    assertEquals("axe", testAxe.getSpriteName());
    assertEquals("bow", testBow.getSpriteName());
    assertEquals("knife", testKnife.getSpriteName());
    assertEquals("staff", testStaff.getSpriteName());
    assertEquals("sword", testSword.getSpriteName());

    checkConstruction(expectedAxe, testAxe,
      new Axe("Axe", DAMAGE, SPEED),
      testStaff);
    checkConstruction(expectedStaff, testStaff,
      new Staff("Staff", DAMAGE, SPEED, MAGIC_DAMAGE),
      testSword);
    checkConstruction(expectedSword, testSword,
      new Sword("Sword", DAMAGE, SPEED),
      testBow);
    checkConstruction(expectedBow, testBow,
      new Bow("Bow", DAMAGE, SPEED),
      testKnife);
    checkConstruction(expectedKnife, testKnife,
      new Knife("Knife", DAMAGE, SPEED),
      testAxe);
  }

  protected void checkConstruction(final IWeapon expectedWeapon,
      final IWeapon testEqualWeapon,
      final IWeapon sameClassDifferentWeapon,
      final IWeapon differentClassWeapon) {
    assertEquals(expectedWeapon, expectedWeapon);
    assertEquals(expectedWeapon, testEqualWeapon);
    assertNotEquals(sameClassDifferentWeapon, testEqualWeapon);
    assertNotEquals(testEqualWeapon, differentClassWeapon);
    assertEquals(expectedWeapon.hashCode(), testEqualWeapon.hashCode());
  }

}