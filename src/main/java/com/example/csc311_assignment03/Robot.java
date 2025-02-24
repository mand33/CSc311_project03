package com.example.csc311_assignment03;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Robot {
    private ImageView imageView;
    private double x, y;
    private final double width = 20;
    private final double height = 20;

    public Robot(String imagePath, double startX, double startY) {
        Image image = new Image(imagePath);
        imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        setPosition(startX, startY);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
    }

    public void setDirection(String direction) {

        switch (direction) {
            case "left":
                imageView.setScaleX(-1); // Flip horizontally
                break;
            case "right":
                imageView.setScaleX(1);
                break;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public ImageView getImageView() {
        return imageView;
    }
}