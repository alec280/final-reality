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
Currently, the project can't be executed as a working game.

Assumptions
-----------
The development of this project assumes that all types of weapon and player character may require
unique properties in the future, therefore, they are implemented with a high amount of subclasses.

It also assumes that the minimum damage value is 1, this ensures that every combat comes to an end
and no character regains health through damage.

Finally, it assumes that player characters can only be equipped with weapons own by the user.

Program summary
---------------
Even though the project can't be executed, its logic can be explained as follows:<br>
- There are two type of characters, player characters (controlled by the user) and enemies 
(controlled by the CPU), both of them have stats.
- There are weapons that can be equipped to player characters in order to change their stats.
- There are status effects that can change the stats of a character or how it behaves.
- Eventually, player characters and enemies will battle, and their stats will decide who wins.