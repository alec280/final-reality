package com.github.alec280.finalreality.gui;

import com.github.alec280.finalreality.controller.GameController;
import com.github.alec280.finalreality.controller.handlers.ControllerHandler;
import com.github.alec280.finalreality.gui.nodes.MovableNode;
import com.github.alec280.finalreality.gui.nodes.MovableNodeBuilder;
import com.github.alec280.finalreality.model.character.Enemy;
import com.github.alec280.finalreality.model.character.ICharacter;
import com.github.alec280.finalreality.model.character.player.IPlayerCharacter;
import com.github.alec280.finalreality.model.weapon.IWeapon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Main entry point for the application.
 * <p>
 * <Complete here with the details of the implemented application>
 *
 * @author Ignacio Slater Muñoz
 * @author Alexander Cuevas.
 */
public class FinalReality extends Application {
  private static final String RESOURCE_PATH = "src/main/resources/";
  private final GameController controller = new GameController();
  private final ControllerHandler controllerHandler = new ControllerHandler(this);
  private final Group root = new Group();
  private final Scene scene = new Scene(root, 1080, 720);

  private final Label description = new Label();
  private final Label weaponDes = new Label();
  private final Label history = new Label();

  private final List<Node> enemySprites = new ArrayList<>();
  private final List<Node> playerSprites = new ArrayList<>();
  private final List<Node> weaponSprites = new ArrayList<>();

  private Node enemyIndicator = new Label();
  private Node playerIndicator = new Label();
  private Node weaponIndicator = new Label();

  private Button startButton = new Button();
  private Button endButton = new Button();
  private Button equipButton = new Button();
  private Button attackButton = new Button();
  private Button equipmentButton = new Button();
  private Button leftButton = new Button();
  private Button rightButton = new Button();

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Final reality");
    primaryStage.setResizable(false);
    setupUIElements();

    controller.addListener(controllerHandler);
    controller.createCuboids();
    controller.createParty();
    controller.createWeapons();

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  /**
   * Shows the turn of the active character, if it is an enemy, it shows its attack.
   */
  public void showCharacterTurn() {
    ICharacter character = controller.getTurnsQueue().peek();
    if (character == null) {
      return;
    }
    if (!controller.isPlayerTurn()) {
      controller.tryToEnemyAttack(character, controller.getUser().getParty());
    } else {
      showPlayerUI();
      drawEnemyIndicator();
      drawPlayerIndicator();
    }
  }

  /**
   * Shows the attack of a character, in text form.
   */
  public void showAttack(final String action) {
    if (!controller.isBattleStarted()) {
      return;
    }
    StringBuilder old_text = new StringBuilder(history.getText());
    if (old_text.length() == 0) {
      history.setText(action);
    } else {
      String[] lines = old_text.toString().split("[\r|\n]");
      if (lines.length == 6) {
        old_text = new StringBuilder();
        for (int i = 1; i < lines.length; i++) {
          old_text.append(lines[i]).append("\n");
        }
      } else {
        old_text.append("\n");
      }
      history.setText(old_text + action);
    }
  }

  /**
   * Deletes the sprite of the enemy at the given index position.
   * It also positions the index at a valid enemy.
   */
  public void deleteEnemy(int idx) {
    root.getChildren().remove(enemySprites.get(idx));
    playSound("death");
  }

  /**
   * Deletes the sprite of the player at the given index position.
   */
  public void deletePlayer(int idx) {
    root.getChildren().remove(playerSprites.get(idx));
    playSound("death");
  }

  /**
   * Ends the current battle, either in victory or defeat.
   */
  public void endBattle(boolean victory) {
    controller.setBattleStarted(false);
    root.getChildren().remove(description);
    if (!victory) {
      history.setText("Your party was defeated...");
    } else {
      history.setText("You have defeated the Cuboids!");
    }
    root.getChildren().add(endButton);
  }

  /**
   * Toggles the inventory on and off.
   */
  public void toggleInventory() {
    playSound("button");
    controller.toggleInventory();
    if (controller.getPhase().canEquip()) {
      root.getChildren().remove(attackButton);
      root.getChildren().remove(enemyIndicator);
      root.getChildren().remove(history);
      root.getChildren().add(equipButton);
      root.getChildren().add(weaponDes);
      IPlayerCharacter player = controller.getUser().getParty().get(controller.getPlayerIdx());
      description.setText(controller.getCharacterInfo(player));
      drawWeapons();
      drawWeaponIndicator();
    } else {
      controller.setWeaponIdx(0);
      root.getChildren().remove(equipButton);
      root.getChildren().remove(weaponIndicator);
      root.getChildren().remove(weaponDes);
      root.getChildren().add(attackButton);
      root.getChildren().add(history);
      for (var sprite : weaponSprites) {
        root.getChildren().remove(sprite);
      }
      drawEnemyIndicator();
    }
  }

  /**
   * Plays a valid sound file.
   */
  public static void playSound(String soundName) {
    String audioFilePath = RESOURCE_PATH + soundName + ".wav";
    try {
      Clip sound = AudioSystem.getClip();
      try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(audioFilePath))) {
        sound.open(audioInputStream);
        sound.start();
      }
    } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
      e.printStackTrace();
    }
  }

  private void setupUIElements () {
    description.setTranslateX(30);
    description.setTranslateY(24);
    description.setStyle("-fx-background-color: black");
    description.setTextFill(Color.WHITE);

    history.setTranslateX(30);
    history.setTranslateY(486);
    history.setStyle("-fx-background-color: black");
    history.setTextFill(Color.WHITE);

    weaponDes.setTranslateX(30);
    weaponDes.setTranslateY(625);
    weaponDes.setStyle("-fx-background-color: black");
    weaponDes.setTextFill(Color.WHITE);

    startButton = startButton();
    endButton = endButton();
    attackButton = attackButton();
    equipmentButton = equipmentButton();
    equipButton = equipButton();
    leftButton = leftButton();
    rightButton = rightButton();

    try {
      Font font = Font.loadFont(new FileInputStream(RESOURCE_PATH + "m5x7.ttf"), 32);
      var background = new ImageView(new Image(new FileInputStream(RESOURCE_PATH + "background.png")));
      leftButton.setGraphic(new ImageView(new Image(new FileInputStream(RESOURCE_PATH + "left.png"))));
      rightButton.setGraphic(new ImageView(new Image(new FileInputStream(RESOURCE_PATH + "right.png"))));
      root.getChildren().add(background);
      root.getChildren().add(startButton);
      description.setFont(font);
      history.setFont(font);
      weaponDes.setFont(font);
      equipButton.setFont(font);
      startButton.setFont(font);
      attackButton.setFont(font);
      equipmentButton.setFont(font);
      endButton.setFont(font);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void drawPlayers() {
    final List<IPlayerCharacter> players = controller.getUser().getParty();
    for (int i = 0; i < players.size(); i++) {
      var node = Objects.requireNonNull(getPlayerSprite(i, players.get(i).getSpriteName())).getNode();
      root.getChildren().add(node);
      playerSprites.add(node);
    }
  }

  private void drawEnemies() {
    final List<Enemy> enemies = controller.getEnemies();
    for (int i = 0; i < enemies.size(); i++) {
      var node = Objects.requireNonNull(getEnemySprite(i)).getNode();
      root.getChildren().add(node);
      enemySprites.add(node);
    }
  }

  private void drawWeapons() {
    var inventory = controller.getUser().getInventory();
    var keySet = inventory.keySet();
    weaponSprites.clear();
    for (IWeapon weapon : keySet) {
      for (int i = 0; i < inventory.get(weapon); i++) {
        var node = Objects.requireNonNull(getWeaponSprite(weaponSprites.size(),
            weapon.getSpriteName())).getNode();
        weaponSprites.add(node);
        root.getChildren().add(node);
      }
    }
  }

  private MovableNode getEnemySprite(int position) {
    try {
      return new MovableNodeBuilder(scene).setImagePath(RESOURCE_PATH + "cuboid.png")
          .setPosition(37 + position * (103 + 20), 305)
          .setSize(123, 103)
          .build();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  private MovableNode getPlayerSprite(int position, String spriteName) {
    try {
      return new MovableNodeBuilder(scene).setImagePath(RESOURCE_PATH + spriteName + ".png")
          .setPosition(537 + 37 + position * (103 + 20), 315)
          .setSize(123, 103)
          .build();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  private MovableNode getWeaponSprite(int position, String spriteName) {
    try {
      return new MovableNodeBuilder(scene).setImagePath(RESOURCE_PATH + spriteName + ".png")
          .setPosition(37 + position * (64 + 15), 525)
          .setSize(64, 64)
          .build();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  private MovableNode getEnemyIndicator(int position) {
    try {
      return new MovableNodeBuilder(scene).setImagePath(RESOURCE_PATH + "arrow.png")
          .setPosition(30 + position * (103 + 20), 305 - 133)
          .setSize(123, 103)
          .build();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  private MovableNode getPlayerIndicator(int position) {
    try {
      return new MovableNodeBuilder(scene).setImagePath(RESOURCE_PATH + "arrow_little.png")
          .setPosition(530 + 75 + position * (103 + 20), 315 - 31)
          .setSize(20, 37)
          .build();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  private MovableNode getWeaponIndicator(int position) {
    try {
      return new MovableNodeBuilder(scene).setImagePath(RESOURCE_PATH + "arrow_little.png")
          .setPosition(53 + position * (64 + 15), 500)
          .setSize(20, 37)
          .build();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  private @NotNull Button commonButton(final @NotNull String text, final int hPos, final int vPos) {
    Button button = new Button(text);
    button.setLayoutX(hPos);
    button.setLayoutY(vPos);
    button.setFocusTraversable(false);
    button.setStyle("-fx-background-color: black");
    button.setTextFill(Color.WHITE);
    return button;
  }

  private @NotNull Button startButton() {
    Button button = commonButton("Start", 890, 480);
    button.setOnAction(event -> startBattle());
    return button;
  }

  private @NotNull Button endButton() {
    Button button = commonButton("Exit", 895, 480);
    button.setOnAction(event -> Platform.exit());
    return button;
  }

  private @NotNull Button attackButton() {
    Button button = commonButton("Attack", 890, 480);
    button.setOnAction(event -> performAttack());
    return button;
  }

  private @NotNull Button equipButton() {
    Button button = commonButton("Equip", 890, 480);
    button.setOnAction(event -> equipWeapon());
    return button;
  }

  private @NotNull Button equipmentButton() {
    Button button = commonButton("Equipment", 870, 535);
    button.setOnAction(event -> toggleInventory());
    return button;
  }

  private @NotNull Button leftButton() {
    Button button = commonButton("", 845, 600);
    button.setOnAction(event -> moveIndicator(true));
    button.setPrefSize(42, 82);
    return button;
  }

  private @NotNull Button rightButton() {
    Button button = commonButton("", 980, 600);
    button.setOnAction(event -> moveIndicator(false));
    button.setPrefSize(42, 82);
    return button;
  }

  private void hidePlayerUI() {
    root.getChildren().remove(attackButton);
    root.getChildren().remove(equipmentButton);
    root.getChildren().remove(enemyIndicator);
    root.getChildren().remove(playerIndicator);
    root.getChildren().remove(leftButton);
    root.getChildren().remove(rightButton);
  }

  private void showPlayerUI() {
    if (!root.getChildren().contains(attackButton)) {
      root.getChildren().add(attackButton);
      root.getChildren().add(equipmentButton);
      root.getChildren().add(leftButton);
      root.getChildren().add(rightButton);
    }
  }

  private void drawEnemyIndicator() {
    root.getChildren().remove(enemyIndicator);
    enemyIndicator = Objects.requireNonNull(getEnemyIndicator(controller.getEnemyIdx())).getNode();
    root.getChildren().add(enemyIndicator);
    description.setText(controller.getCharacterInfo(controller.getEnemies().get(controller.getEnemyIdx())));
  }

  private void drawPlayerIndicator() {
    root.getChildren().remove(playerIndicator);
    playerIndicator = Objects.requireNonNull(getPlayerIndicator(controller.getPlayerIdx())).getNode();
    root.getChildren().add(playerIndicator);
  }

  private void drawWeaponIndicator() {
    root.getChildren().remove(weaponIndicator);
    weaponIndicator = Objects.requireNonNull(getWeaponIndicator(controller.getWeaponIdx())).getNode();
    root.getChildren().add(weaponIndicator);
    weaponDes.setText(controller.getWeaponInfo(controller.getWeapon(controller.getWeaponIdx())));
  }

  private void moveIndicator(boolean left) {
    playSound("button");
    if (!controller.getPhase().canEquip()) {
      if (left) {
        controller.setEnemyIdx(controller.getEnemyIdx() - 1);
      } else {
        controller.setEnemyIdx(controller.getEnemyIdx() + 1);
      }
      drawEnemyIndicator();
    } else {
      if (left) {
        controller.setWeaponIdx(controller.getWeaponIdx() - 1);
      } else {
        controller.setWeaponIdx(controller.getWeaponIdx() + 1);
      }
      drawWeaponIndicator();
    }
  }

  private void startBattle() {
    if (controller.isBattleStarted()) {
      return;
    }
    controller.setBattleStarted(true);
    controller.startWait();
    root.getChildren().add(description);
    root.getChildren().add(history);
    root.getChildren().remove(startButton);
    playSound("button");
    drawEnemies();
    drawPlayers();
  }

  private void performAttack() {
    playSound("button");
    hidePlayerUI();
    ICharacter player = controller.getUser().getParty().get(controller.getPlayerIdx());
    Enemy enemy = controller.getEnemies().get(controller.getEnemyIdx());
    controller.tryToPerformAttack(player, enemy);
  }

  private void equipWeapon() {
    IPlayerCharacter player = controller.getUser().getParty().get(controller.getPlayerIdx());
    IWeapon weapon = controller.getWeapon(controller.getWeaponIdx());
    controller.tryToEquipWeapon(weapon, player);
  }

}