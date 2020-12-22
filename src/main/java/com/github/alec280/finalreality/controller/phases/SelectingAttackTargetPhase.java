package com.github.alec280.finalreality.controller.phases;

import com.github.alec280.finalreality.model.character.ICharacter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * This represents the phase of the game where a character can attack another,
 * in the case of a player character, this represents the phase where the user can manage it.
 *
 * @author Alexander Cuevas.
 */
public class SelectingAttackTargetPhase extends AbstractPhase {

  /**
   * Creates a new SelectingAttackTargetPhase.
   */
  public SelectingAttackTargetPhase() {
    this.canAttack = true;
  }

  @Override
  public void toIdlePhase() {
    changePhase(new IdlePhase());
  }

  @Override
  public void toChangingWeaponPhase() {
    changePhase(new ChangingWeaponPhase());
  }

  @Override
  public void performAttack(@NotNull ICharacter aggressor, @NotNull ICharacter defender)
      throws InvalidActionException {
    super.performAttack(aggressor, defender);
  }

  @Override
  public void toggleInventory() {
    toChangingWeaponPhase();
  }

  @Override
  public String toString() {
    return "Selecting Attack Target Phase";
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SelectingAttackTargetPhase)) {
      return false;
    }
    final SelectingAttackTargetPhase phase = (SelectingAttackTargetPhase) o;
    return toString().equals(phase.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(toString());
  }
}
