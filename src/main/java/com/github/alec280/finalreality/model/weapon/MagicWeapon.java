package com.github.alec280.finalreality.model.weapon;

import java.util.Objects;

public class MagicWeapon extends Weapon {

  private final int magicDamage;

  /**
  * Creates a MagicWeapon with a name, base damage, speed and magic damage.
  * Since only staves are magic weapons, the type is inferred.
  *
  * @see WeaponType
  */
  public MagicWeapon(final String name, final int damage, final int weight,
      final int magicDamage) {
    super(name, damage, weight, WeaponType.STAFF);
    this.magicDamage = magicDamage;
  }

  private int getMagicDamage() {
        return magicDamage;
    }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof MagicWeapon)) {
      return false;
    }
    final MagicWeapon weapon = (MagicWeapon) o;
    return getDamage() == weapon.getDamage() &&
      getWeight() == weapon.getWeight() &&
      getName().equals(weapon.getName()) &&
      getMagicDamage() == weapon.getMagicDamage();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getDamage(), getWeight(), getMagicDamage());
  }

}
