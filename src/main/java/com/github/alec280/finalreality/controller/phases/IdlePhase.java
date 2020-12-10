package com.github.alec280.finalreality.controller.phases;

import java.util.Objects;

/**
 * This represents the phase of the game where no character is active.
 *
 * @author Alexander Cuevas.
 */
public class IdlePhase extends AbstractPhase {

  /**
   * Creates a new IdlePhase.
   */
  public IdlePhase() {}

  @Override
  public void toSelectingAttackTargetPhase() {
    changePhase(new SelectingAttackTargetPhase());
  }

  @Override
  public String toString() {
    return "Idle Phase";
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof IdlePhase)) {
      return false;
    }
    final IdlePhase phase = (IdlePhase) o;
    return toString().equals(phase.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(toString());
  }
}
