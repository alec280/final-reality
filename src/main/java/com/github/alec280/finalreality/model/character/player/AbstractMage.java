package com.github.alec280.finalreality.model.character.player;

import com.github.alec280.finalreality.model.character.ICharacter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.BlockingQueue;

/**
 * An abstract class that holds the common behaviour of all the mages in the game.
 *
 * @author Alexander Cuevas.
 */
public abstract class AbstractMage extends AbstractPlayer {

  private final int maxMana;
  private int mana;

  protected AbstractMage(@NotNull final String name, final int maxHealth, final int defense,
      final int maxMana, @NotNull final BlockingQueue<ICharacter> turnsQueue) {
    super(name, maxHealth, defense, turnsQueue);
    this.maxMana = maxMana;
    this.mana = maxMana;
  }

  /**
   * Returns this wizard's maximum mana.
   */
  public int getMaxMana() {
    return maxMana;
  }

  /**
   * Returns this wizard's current mana.
   */
  public int getMana() {
    return mana;
  }

}
