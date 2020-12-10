package com.github.alec280.finalreality.controller.phases;

import com.github.alec280.finalreality.controller.GameController;
import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.character.player.IPlayerCharacter;
import com.github.alec280.finalreality.model.weapon.IWeapon;
import org.jetbrains.annotations.NotNull;

/**
 * This represents a phase of the game flow.
 *
 * @author Alexander Cuevas.
 */
public interface IPhase {

  /**
   * Sets the controller of this phase.
   */
  void setController(final @NotNull GameController controller);

  /**
   * Changes the current phase to the Changing Weapon Phase.
   */
  void toChangingWeaponPhase() throws InvalidTransitionException;

  /**
   * Changes the current phase to the Selecting Attack Target Phase.
   */
  void toSelectingAttackTargetPhase() throws InvalidTransitionException;

  /**
   * Changes the current phase to the Idle Phase.
   */
  void toIdlePhase() throws InvalidTransitionException;

  /**
   * Performs and attack between characters.
   */
  void performAttack(@NotNull ICharacter aggressor, @NotNull ICharacter defender) throws InvalidActionException;

  /**
   * Changes the phase to or from the Changing Weapon Phase.
   */
  void toggleInventory() throws InvalidActionException;

  /**
   * Equips a weapon to a player character.
   */
  void equipWeapon(@NotNull IWeapon weapon, @NotNull IPlayerCharacter player) throws InvalidActionException;
}
