package com.github.alec280.finalreality.gui.nodes;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This class represents an object that can be moved inside the game.
 *
 * @author Ignacio Slater Muñoz
 * @version 3.0b9
 * @since 3.0
 */
public class MovableNode {
  private final int imgHeight;
  private final int imgWidth;
  private ImageView sprite;
  private int hPos;
  private int vPos;

  /**
   * Creates a new movable component from an image.
   */
  public MovableNode(final @NotNull Scene scene, final int hPos, final int vPos,
                     final int imgHeight, final int imgWidth, final String spritePath)
      throws FileNotFoundException {
    this.imgHeight = imgHeight;
    this.imgWidth = imgWidth;
    this.hPos = hPos;
    this.vPos = vPos;
    addSprite(spritePath);
    scene.setOnKeyPressed(this::setKeyboardTriggers);
    startAnimator();
  }

  private void addSprite(final String spritePath) throws FileNotFoundException {
    FileInputStream spriteImage = new FileInputStream(spritePath);
    sprite = new ImageView(new Image(spriteImage));
    sprite.setX(hPos);
    sprite.setY(vPos);
    sprite.setFitWidth(imgWidth);
    sprite.setFitHeight(imgHeight);
  }

  private void startAnimator() {
    AnimationTimer timer = new AnimationTimer() {
      @Override
      public void handle(final long now) {
        sprite.setX(hPos);
        sprite.setY(vPos);
      }
    };
    timer.start();
  }

  /**
   * Moves the component down.
   */
  public void moveDown(int verticalDistance) {
    move(0, verticalDistance);
  }

  /**
   * Moves the component up.
   */
  public void moveUp(int verticalDistance) {
    move(0, -verticalDistance);
  }

  /**
   * Moves the component down.
   */
  public void moveRight(int horizontalDistance) {
    move(horizontalDistance, 0);
  }

  /**
   * Moves the component up.
   */
  public void moveLeft(int horizontalDistance) {
    move(-horizontalDistance, 0);
  }

  /**
   * Moves the component to a new position
   *
   * @param horizontalDistance
   *     the horizontal movement
   * @param verticalDistance
   *     the vertical movement
   */
  private void move(int horizontalDistance, int verticalDistance) {
    hPos += horizontalDistance;
    vPos += verticalDistance;
  }

  public ImageView getNode() {
    return sprite;
  }

  private void setKeyboardTriggers(@NotNull KeyEvent event) {
    switch (event.getCode()) {
      case UP:
        moveUp(imgHeight);
        break;
      case DOWN:
        moveDown(imgHeight);
        break;
      case RIGHT:
        moveRight(imgWidth);
        break;
      case LEFT:
        moveLeft(imgWidth);
        break;
      default:
        break;
    }
  }
}
