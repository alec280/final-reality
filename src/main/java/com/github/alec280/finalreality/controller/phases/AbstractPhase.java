package com.github.alec280.finalreality.controller.phases;

import com.github.alec280.finalreality.controller.GameController;
import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.character.player.IPlayerCharacter;
import com.github.alec280.finalreality.model.weapon.IWeapon;
import org.jetbrains.annotations.NotNull;

/**
 * This represents a phase of the game, according to certain conditions, it can transition
 * the game flow to another phase.
 *
 * @author Alexander Cuevas.
 */
public abstract class AbstractPhase implements IPhase {
  protected GameController controller;
  protected boolean canAttack = false;
  protected boolean canEquip = false;

  protected void changePhase(IPhase phase) {
    controller.setPhase(phase);
  }

  @Override
  public void setController(final @NotNull GameController controller) {
    this.controller = controller;
  }

  @Override
  public void toChangingWeaponPhase() throws InvalidTransitionException {
    throw new InvalidTransitionException(
        "Can't change from " + this.toString() + " to Changing Weapon Phase");
  }

  @Override
  public void toSelectingAttackTargetPhase() throws InvalidTransitionException {
    throw new InvalidTransitionException(
        "Can't change from " + this.toString() + " to Selecting Attack Target Phase");
  }

  @Override
  public void toIdlePhase() throws InvalidTransitionException {
    throw new InvalidTransitionException(
        "Can't change from " + this.toString() + " to Idle Phase");
  }

  @Override
  public void performAttack(@NotNull ICharacter aggressor, @NotNull ICharacter defender)
      throws InvalidActionException {
    if (!canAttack) {
      throw new InvalidActionException("You can't attack now.");
    }
    controller.performAttack(aggressor, defender);
  }

  @Override
  public void toggleInventory() throws InvalidActionException {
    throw new InvalidActionException("Can't toggle the inventory on " + this.toString() + ".");
  }

  @Override
  public void equipWeapon(@NotNull IWeapon weapon, @NotNull IPlayerCharacter player)
      throws InvalidActionException {
    if (!canEquip) {
      throw new InvalidActionException("You can't equip weapons now.");
    }
    controller.equipWeapon(weapon, player);
  }

}
