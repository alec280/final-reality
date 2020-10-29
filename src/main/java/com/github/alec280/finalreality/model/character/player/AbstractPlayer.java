package com.github.alec280.finalreality.model.character.player;

import com.github.alec280.finalreality.model.character.AbstractCharacter;
import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.weapon.IWeapon;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.BlockingQueue;

/**
 * An abstract class that holds the common behaviour of all the players in the game.
 *
 * @author Alexander Cuevas.
 */
public abstract class AbstractPlayer extends AbstractCharacter {

  private IWeapon equippedWeapon = null;

  protected AbstractPlayer(@NotNull String name, final int maxHealth, final int defense,
      @NotNull BlockingQueue<ICharacter> turnsQueue) {
    super(name, maxHealth, defense, turnsQueue);
  }

  /**
   * Equips a weapon to the player.
   */
  public void equip(@NotNull IWeapon weapon) {
    equippedWeapon = weapon;
  }

  /**
   * Return this player's equipped weapon.
   */
  public IWeapon getEquippedWeapon() {
    return equippedWeapon;
  }

  @Override
  public boolean canEquip(@NotNull IWeapon weapon) {
    return isAlive();
  }

  @Override
  public int getAttack() {
    if (getEquippedWeapon() == null) {
      return super.getAttack();
    }
    return getEquippedWeapon().getAttack();
  }

  @Override
  public int getWeight() {
    if (getEquippedWeapon() == null) {
      return super.getWeight();
    }
    return getEquippedWeapon().getWeight();
  }

}
