package com.github.alec280.finalreality.controller.phases;

import com.github.alec280.finalreality.model.character.player.IPlayerCharacter;
import com.github.alec280.finalreality.model.weapon.IWeapon;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * This represents the phase of the game where the user can equip and change weapons.
 *
 * @author Alexander Cuevas.
 */
public class ChangingWeaponPhase extends AbstractPhase {

  /**
   * Creates a new ChangingWeaponPhase.
   */
  public ChangingWeaponPhase() {
    this.canEquip = true;
  }

  @Override
  public void toSelectingAttackTargetPhase() {
    changePhase(new SelectingAttackTargetPhase());
  }

  @Override
  public void equipWeapon(@NotNull IWeapon weapon, @NotNull IPlayerCharacter player)
      throws InvalidActionException {
    super.equipWeapon(weapon, player);
  }

  @Override
  public void toggleInventory() {
    toSelectingAttackTargetPhase();
  }

  @Override
  public String toString() {
    return "Changing Weapon Phase";
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ChangingWeaponPhase)) {
      return false;
    }
    final ChangingWeaponPhase phase = (ChangingWeaponPhase) o;
    return toString().equals(phase.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(toString());
  }
}
