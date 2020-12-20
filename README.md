Final Reality
=============

![http://creativecommons.org/licenses/by/4.0/](https://i.creativecommons.org/l/by/4.0/88x31.png)

This work is licensed under a 
[Creative Commons Attribution 4.0 International License](http://creativecommons.org/licenses/by/4.0/)

Context
-------

This project's goal is to create a (simplified) clone of _Final Fantasy_'s combat, a game developed
by [_Square Enix_](https://www.square-enix.com).
Broadly speaking for the combat the player has a group of characters to control, and a group of 
enemies controlled by the computer.

---

Execution instructions
----------------------
Currently, the project can be executed by running the FinalReality class, which contains an entry point
through its main function.

Once executed, the user can interact with the program via buttons found in the lower right corner of the window
associated with the program, the buttons may vary depending on the phase of the game, but they are always
labeled (through text or icons) with the action they will execute inside the game.

A summary of all buttons and their actions:<br>
- Start: Starts a battle.
- Attack: Attacks the selected enemy with the current player character.
- Equipment: Opens and closes the inventory.
- Equip: Tries to equip the selected weapon to the current player character. If possible, the inventory will close
on its own, if not, failure will be communicated through an annoying sound.
- Left arrow: Changes the selected enemy (or weapon, if the inventory is open) to one at the left of the currently 
selected one, going to the far right only if there isn't anything on the left side.
- Right arrow: Same action than the Left arrow, but favoring the right direction.
- Exit: Closes the program.

Assumptions
-----------
The development of this project assumes that all types of weapon and player character may require
unique properties in the future, therefore, they are implemented with a high amount of subclasses.

It also assumes that the minimum damage value is 1, this ensures that every combat comes to an end
and no character regains health through damage.

It also assumes that player characters can only be equipped with weapons own by the user,
meaning they are pull from the user's inventory, furthermore, it assumes that the inventory
contains enough weapons to equip the entire party and never be empty.

Finally, it assumes that dead player characters don't return their weapons to the user's invetory.

Program summary
---------------
The logic of the program can be explained as follows:<br>
- The program begins with a start screen that requires user input in order to proceed with a battle.
- When starting a battle, two type of characters are created, player characters (controlled by the user) and enemies 
(controlled by the CPU), both of them have stats (health, defense, attack and weight).
- All characters take turns, thus becoming active, at a speed determined by their weight, 
only one character can be active at a time. After taking its turn, a character becomes inactive until its next turn.
- An active enemy will attack a random player character.
- Attacking reduces the health of the target by an amount determined by the attack and defense of the characters 
involved. When a character's health drops to zero, it is removed from the game.
- An active player character will attack an enemy selected by the user, additionally, it can change its weapon
to one selected by the user, if the character knows how to use it. A successful change of weapons will alter its 
attack and weight.
- The user inputs its decisions trough buttons found in the lower right corner of the screen.
- When all enemies are removed from the game, the user wins.
- When all player characters are removed from the game, the user loses.
- Whether the user wins or loses, they will be sent to an end screen, where they can use a button to exit the program.