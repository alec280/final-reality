package com.github.alec280.finalreality.model.character.player;

import com.github.alec280.finalreality.model.character.ICharacter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.BlockingQueue;

public class MagicPlayerCharacter extends PlayerCharacter{

  private final int maxMana;
  private int mana;

  /**
   * Creates a new player character with mana.
   */
  public MagicPlayerCharacter(@NotNull final String name, final int maxHealth, final int defense, final int maxMana,
      final CharacterClass characterClass, @NotNull final BlockingQueue<ICharacter> turnsQueue) {
    super(name, maxHealth, defense, characterClass, turnsQueue);
    this.maxMana = maxMana;
    this.mana = maxMana;
  }

  /**
   * Returns this character's maximum mana.
   */
  private int getMaxMana() { return maxMana; }

  /**
   * Returns this character's current mana.
   */
  private int getMana() { return mana; }

}
