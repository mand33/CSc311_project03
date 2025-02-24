package com.example.csc311_assignment03;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class MazeImage {
    private Image image;

    public MazeImage(String path) {
        this.image = new Image(getClass().getResourceAsStream(path));
    }

    public Image getImage() {
        return image;
    }

    public boolean isPathPixel(double x, double y) {
        PixelReader pixelReader = image.getPixelReader();
        Color color = pixelReader.getColor((int) x, (int) y);
        return color.equals(Color.WHITE);
    }
}