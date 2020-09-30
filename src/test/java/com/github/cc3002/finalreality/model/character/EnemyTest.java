package com.github.cc3002.finalreality.model.character;

import com.github.alec280.finalreality.model.character.Enemy;
import com.github.alec280.finalreality.model.character.player.Thief;
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
        new Enemy("Hobgoblin", 5, 3, 3,10, turns),
        new Thief(ENEMY_NAME, 5, 3, turns));
  }
}