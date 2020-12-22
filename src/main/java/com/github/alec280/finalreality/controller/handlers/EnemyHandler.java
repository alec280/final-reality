package com.github.alec280.finalreality.controller.handlers;

import com.github.alec280.finalreality.controller.GameController;
import com.github.alec280.finalreality.model.character.Enemy;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This handles changes in enemies, notifying its controller whenever it is necessary.
 *
 * @see Enemy
 * @author Alexander Cuevas.
 */
public class EnemyHandler implements PropertyChangeListener {
  private final GameController controller;

  /**
   * Creates a new EnemyHandler, tied to a specific controller.
   */
  public EnemyHandler(GameController controller) {
    this.controller = controller;
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals("health")) {
      if ((int) evt.getNewValue() == 0) {
        controller.onEnemyDeath();
      }
    } else if (evt.getPropertyName().equals("turn")) {
      if ((boolean) evt.getNewValue()) {
        controller.onTurnStarted(false);
      } else {
        controller.onTurnEnded();
      }
    }
  }
}