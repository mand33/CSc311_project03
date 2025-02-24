package com.example.csc311_assignment03;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;

public class Car {
    private Group shape;
    private Polygon body;
    private Rectangle window1, window2;
    private Circle wheel1, wheel2;
    private double x, y;
    private final double width = 40;
    private final double height = 20;

    public Car(double startX, double startY, String startDirection) {
        shape = new Group();

        // Car body
        body = new Polygon();
        body.getPoints().addAll(
                0.0, 0.0,
                width, 0.0,
                width, height * 0.8,
                width * 0.8, height,
                width * 0.2, height,
                0.0, height * 0.8
        );
        body.setFill(Color.PURPLE);

        // Windows
        window1 = new Rectangle(width * 0.2, height * 0.2, width * 0.15, height * 0.3);
        window1.setFill(Color.LIGHTGREEN);

        window2 = new Rectangle(width * 0.5, height * 0.2, width * 0.2, height * 0.3);
        window2.setFill(Color.LIGHTGREEN);

        // Wheels
        wheel1 = new Circle(width * 0.25, height * 1.1, height * 0.3);
        wheel1.setFill(Color.BLACK);

        wheel2 = new Circle(width * 0.75, height * 1.1, height * 0.3);
        wheel2.setFill(Color.BLACK);

        shape.getChildren().addAll(body, window1, window2, wheel1, wheel2);
        setPosition(startX, startY);
        setDirection(startDirection);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }

    public void setDirection(String direction) {
        // Reset rotation
        shape.setRotate(0);

        // Set rotation based on direction
        switch (direction) {
            case "up":
                shape.setRotate(-90);
                break;
            case "down":
                shape.setRotate(90);
                break;
            case "left":
                shape.setRotate(180);
                break;
            case "right":
                // Default orientation
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

    public Group getShape() {
        return shape;
    }
}