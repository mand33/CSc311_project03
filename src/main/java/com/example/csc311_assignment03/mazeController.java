package com.example.csc311_assignment03;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.animation.AnimationTimer;
import java.util.ArrayList;
import java.util.List;

public class mazeController {

    @FXML private TabPane tabPane;
    @FXML private Tab maze1Tab;
    @FXML private Tab maze2Tab;
    @FXML private AnchorPane maze1Pane;
    @FXML private AnchorPane maze2Pane;
    @FXML private ImageView maze1ImageView;
    @FXML private ImageView maze2ImageView;
    @FXML private Button startAnimationButton;
    @FXML private Button resetButton;

    private Robot robot;
    private Car car;
    private MazeImage maze1;
    private MazeImage maze2;
    private AnimationTimer animationTimer;
    private boolean animationRunning = false;
    private List<PathCoordinate> maze1Path;
    private List<PathCoordinate> maze2Path;
    private int currentPathIndex = 0;

    @FXML
    public void initialize() {
        // Load maze images
        maze1 = new MazeImage("maze.png");
        maze2 = new MazeImage("maze2.png");

        maze1ImageView.setImage(maze1.getImage());
        maze2ImageView.setImage(maze2.getImage());

        // Initialize robot in maze1
        robot = new Robot("robot.png", 10, 10);
        maze1Pane.getChildren().add(robot.getImageView());

        // Initialize car in maze2
        car = new Car(10, 10, "right");
        maze2Pane.getChildren().add(car.getShape());

        // Setup key listeners for manual navigation
        setupKeyListeners();

        // Setup animation button
        startAnimationButton.setOnAction(e -> toggleAnimation());

        // Setup reset button
        resetButton.setOnAction(e -> resetPositions());

        // Pre-compute paths for animation
        computePaths();
    }

    private void setupKeyListeners() {
        maze1Pane.setFocusTraversable(true);
        maze1Pane.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (animationRunning) return;

            switch (e.getCode()) {
                case UP:
                    moveRobot(0, -5);
                    break;
                case DOWN:
                    moveRobot(0, 5);
                    break;
                case LEFT:
                    moveRobot(-5, 0);
                    robot.setDirection("left");
                    break;
                case RIGHT:
                    moveRobot(5, 0);
                    robot.setDirection("right");
                    break;
                default:
                    break;
            }
            e.consume();
        });

        maze2Pane.setFocusTraversable(true);
        maze2Pane.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (animationRunning) return;

            switch (e.getCode()) {
                case UP:
                    moveCar(0, -5);
                    car.setDirection("up");
                    break;
                case DOWN:
                    moveCar(0, 5);
                    car.setDirection("down");
                    break;
                case LEFT:
                    moveCar(-5, 0);
                    car.setDirection("left");
                    break;
                case RIGHT:
                    moveCar(5, 0);
                    car.setDirection("right");
                    break;
                default:
                    break;
            }
            e.consume();
        });
    }

    private void moveRobot(int deltaX, int deltaY) {
        double newX = robot.getX() + deltaX;
        double newY = robot.getY() + deltaY;

        if (isValidMove(maze1, newX, newY, robot.getWidth(), robot.getHeight())) {
            robot.setPosition(newX, newY);
        }
    }

    private void moveCar(int deltaX, int deltaY) {
        double newX = car.getX() + deltaX;
        double newY = car.getY() + deltaY;

        if (isValidMove(maze2, newX, newY, car.getWidth(), car.getHeight())) {
            car.setPosition(newX, newY);
        }
    }

    private boolean isValidMove(MazeImage maze, double x, double y, double width, double height) {
        // Check if all corners of the object are on valid paths (white pixels)
        return maze.isPathPixel(x, y) &&
                maze.isPathPixel(x + width, y) &&
                maze.isPathPixel(x, y + height) &&
                maze.isPathPixel(x + width, y + height);
    }

    private void toggleAnimation() {
        if (animationRunning) {
            stopAnimation();
            startAnimationButton.setText("Start Animation");
        } else {
            startAnimation();
            startAnimationButton.setText("Stop Animation");
        }
    }

    private void startAnimation() {
        animationRunning = true;
        currentPathIndex = 0;

        animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 50_000_000) { // Update every 50 ms
                    lastUpdate = now;

                    if (tabPane.getSelectionModel().getSelectedItem() == maze1Tab) {
                        animateMaze1();
                    } else {
                        animateMaze2();
                    }
                }
            }
        };

        animationTimer.start();
    }

    private void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        animationRunning = false;
    }

    private void resetPositions() {
        robot.setPosition(10, 10);
        car.setPosition(10, 10);
        currentPathIndex = 0;
    }

    private void animateMaze1() {
        if (currentPathIndex < maze1Path.size()) {
            PathCoordinate coord = maze1Path.get(currentPathIndex);
            robot.setPosition(coord.x, coord.y);
            robot.setDirection(coord.direction);
            currentPathIndex++;
        } else {
            stopAnimation();
            startAnimationButton.setText("Start Animation");
        }
    }

    private void animateMaze2() {
        if (currentPathIndex < maze2Path.size()) {
            PathCoordinate coord = maze2Path.get(currentPathIndex);
            car.setPosition(coord.x, coord.y);
            car.setDirection(coord.direction);
            currentPathIndex++;
        } else {
            stopAnimation();
            startAnimationButton.setText("Start Animation");
        }
    }

    // Simple path-finding algorithm (this would be replaced with actual path finding)
    private void computePaths() {
        // These would typically come from a path-finding algorithm
        // Hardcoded for simplicity
        maze1Path = new ArrayList<>();
        maze1Path.add(new PathCoordinate(10, 10, "right"));
        maze1Path.add(new PathCoordinate(20, 10, "right"));
        // Add more coordinates to complete the path through the maze

        maze2Path = new ArrayList<>();
        maze2Path.add(new PathCoordinate(10, 10, "right"));
        maze2Path.add(new PathCoordinate(20, 10, "right"));
        // Add more coordinates to complete the path through the maze
    }

    // Inner class to represent path coordinates
    private static class PathCoordinate {
        double x, y;
        String direction;

        PathCoordinate(double x, double y, String direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }
}