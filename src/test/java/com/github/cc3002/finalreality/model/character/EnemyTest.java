package com.github.cc3002.finalreality.model.character;

import com.github.alec280.finalreality.model.character.Enemy;
import com.github.alec280.finalreality.model.character.player.CharacterClass;
import com.github.alec280.finalreality.model.character.player.PlayerCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnemyTest extends AbstractCharacterTest {

  private static final String ENEMY_NAME = "Goblin";

  @BeforeEach
  void setUp() {
    basicSetUp();
    testCharacters.add(new Enemy(ENEMY_NAME, 5, 3, 3, 10, turns));
  }

  @Test
  void constructorTest() {
    checkConstruction(new Enemy(ENEMY_NAME, 5, 3, 3, 10, turns),
        testCharacters.get(0),
        new Enemy(ENEMY_NAME, 4, 2, 2,11, turns),
        new PlayerCharacter(ENEMY_NAME, 4, 2, CharacterClass.THIEF, turns));
  }
}