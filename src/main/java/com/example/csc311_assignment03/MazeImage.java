package com.example.csc311_assignment03;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class MazeImage {
    private Image image;
    private PixelReader pixelReader;

    public MazeImage(String imagePath) {
        image = new Image(imagePath);
        pixelReader = image.getPixelReader();
    }

    public boolean isPathPixel(double x, double y) {
        // Make sure coordinates are within image bounds
        if (x < 0 || y < 0 || x >= image.getWidth() || y >= image.getHeight()) {
            return false;
        }

        // Check if the pixel at (x,y) is white (path)
        Color color = pixelReader.getColor((int) x, (int) y);

        // White pixels typically have high RGB values
        return color.getRed() > 0.8 && color.getGreen() > 0.8 && color.getBlue() > 0.8;
    }

    public Image getImage() {
        return image;
    }
}