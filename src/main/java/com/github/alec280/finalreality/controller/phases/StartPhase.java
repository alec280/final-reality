package com.github.alec280.finalreality.controller.phases;

import java.util.Objects;

/**
 * This represents the phase of the game before any battle has begun.
 *
 * @author Alexander Cuevas.
 */
public class StartPhase extends AbstractPhase {

  /**
   * Creates a new StartPhase.
   */
  public StartPhase() {}

  @Override
  public void toIdlePhase() {
    changePhase(new IdlePhase());
  }

  @Override
  public String toString() {
    return "Start Phase";
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof StartPhase)) {
      return false;
    }
    final StartPhase phase = (StartPhase) o;
    return toString().equals(phase.toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(toString());
  }
}
