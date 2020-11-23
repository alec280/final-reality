package com.github.alec280.finalreality.controller;

import com.github.alec280.finalreality.model.character.player.IPlayerCharacter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This handles changes in player characters, notifying its controller whenever it is necessary.
 *
 * @see IPlayerCharacter
 * @author Alexander Cuevas.
 */
public class PlayerHandler implements PropertyChangeListener {
  private final GameController controller;

  /**
   * Creates a new PlayerHandler, tied to a specific controller.
   */
  public PlayerHandler(GameController controller) {
    this.controller = controller;
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (evt.getPropertyName().equals("health")) {
      if ((int) evt.getNewValue() == 0) {
        controller.onPlayerDeath();
      }
    } else if (evt.getPropertyName().equals("turn")) {
      if ((boolean) evt.getNewValue()) {
        controller.onTurnStarted(true);
      } else {
        controller.onTurnEnded();
      }
    }
  }
}
