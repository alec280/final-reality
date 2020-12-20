package com.github.alec280.finalreality.controller.handlers;

import com.github.alec280.finalreality.controller.GameController;
import com.github.alec280.finalreality.gui.FinalReality;
import javafx.application.Platform;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This handles changes in the controller, notifying its view whenever it is necessary.
 *
 * @see GameController
 * @author Alexander Cuevas.
 */
public class ControllerHandler implements PropertyChangeListener {
  private final FinalReality view;

  /**
   * Creates a new ControllerHandler, tied to a specific view.
   */
  public ControllerHandler(FinalReality view) {
    this.view = view;
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    final String property = evt.getPropertyName();
    if (property.equals("activeCharacter")) {
      Platform.runLater(view::showCharacterTurn);
    } else if (property.equals("action")) {
      Platform.runLater(() -> view.showAttack((String) evt.getNewValue()));
    } else if (property.equals("enemyDeath")) {
      Platform.runLater(() -> view.deleteEnemy((int) evt.getNewValue()));
    } else if (property.equals("playerDeath")) {
      Platform.runLater(() -> view.deletePlayer((int) evt.getNewValue()));
    } else if (property.equals("playerVictory")) {
      Platform.runLater(() -> view.endBattle((boolean) evt.getNewValue()));
    } else if (property.equals("weaponChanged")) {
      Platform.runLater(view::toggleInventory);
    } else if (property.equals("unsuccessfulAction")) {
      Platform.runLater(() -> FinalReality.playSound("error"));
    }
  }
}