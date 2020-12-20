package com.github.alec280.finalreality.controller;

import com.github.alec280.finalreality.model.character.player.IPlayerCharacter;
import com.github.alec280.finalreality.model.weapon.IWeapon;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This represents a user, or player, of the game.
 * A user has an inventory of weapons and a party of player characters.
 *
 * @author Alexander Cuevas.
 */
public class User {
  private final Map<IWeapon, Integer> inventory;
  private final List<IPlayerCharacter> party;

  /**
   * Creates a new User, with an empty inventory and party.
   */
  public User() {
    this.inventory = new HashMap<>();
    this.party = new ArrayList<>();
  }

  /**
   * Adds a weapon to this user's inventory.
   */
  public void addWeapon(@NotNull IWeapon weapon) {
    if (inventory.containsKey(weapon)) {
      final int old_value = inventory.get(weapon);
      inventory.replace(weapon, old_value + 1);
    } else {
      inventory.put(weapon, 1);
    }
  }

  /**
   * Removes a weapon from this user's inventory.
   */
  public void removeWeapon(@NotNull IWeapon weapon) {
    final int old_value = inventory.get(weapon);
    if (old_value == 1) {
      inventory.remove(weapon);
    }
    inventory.replace(weapon, old_value - 1);
  }

  /**
   * Returns the size of the inventory, counting every instance of every weapon in it.
   */
  public int getInventorySize() {
    int count = 0;
    var keySet = inventory.keySet();
    for (IWeapon weapon : keySet) {
      count += inventory.get(weapon);
    }
    return count;
  }

  /**
   * Adds a player character to this user's party.
   */
  public void addPlayerCharacter(@NotNull IPlayerCharacter character) {
    party.add(character);
  }

  /**
   * Returns this user's party.
   */
  public List<IPlayerCharacter> getParty() {
    return party;
  }

  /**
   * Returns this user's inventory.
   */
  public Map<IWeapon, Integer> getInventory() {
    return inventory;
  }
}
