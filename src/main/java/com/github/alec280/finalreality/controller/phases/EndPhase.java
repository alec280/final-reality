package com.github.alec280.finalreality.controller.phases;

import java.util.Objects;

/**
 * This represents the phase of the game after the user has lost or won a battle.
 *
 * @author Alexander Cuevas.
 */
public class EndPhase extends AbstractPhase {

  /**
   * Creates a new EndPhase.
   */
  public EndPhase() {
    this.canContinue = false;
  }

  @Override
  public String toString() {
    return "End Phase";
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof EndPhase)) {
      return false;
    }
    final EndPhase phase = (EndPhase) o;
    return toString().equals(phase.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(toString());
  }
}
