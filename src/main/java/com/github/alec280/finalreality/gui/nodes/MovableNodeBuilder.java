package com.github.alec280.finalreality.gui.nodes;

import javafx.scene.Scene;

import java.io.FileNotFoundException;

public class MovableNodeBuilder {
  private final Scene scene;
  private int hPos;
  private int vPos;
  private int height;
  private int width;
  private String imagePath;

  public MovableNodeBuilder(Scene scene) {
    this.scene = scene;
  }

  public MovableNodeBuilder setPosition(int hPos, int vPos) {
    this.hPos = hPos;
    this.vPos = vPos;
    return this;
  }

  public MovableNodeBuilder setSize(int height, int width) {
    this.height = height;
    this.width = width;
    return this;
  }

  public MovableNodeBuilder setImagePath(String path) {
    this.imagePath = path;
    return this;
  }

  public MovableNode build() throws FileNotFoundException {
    return new MovableNode(scene, hPos, vPos, height, width, imagePath);
  }
}
