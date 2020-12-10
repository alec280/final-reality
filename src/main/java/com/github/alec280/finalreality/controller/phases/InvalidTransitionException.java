package com.github.alec280.finalreality.controller.phases;

/**
 * Exception related to problems when transitioning between phases.
 *
 * @author Alexander Cuevas.
 */
public class InvalidTransitionException extends Exception {

  /**
   * Creates a new InvalidTransitionException with the given message.
   */
  public InvalidTransitionException(String message) {
    super(message);
  }
}
