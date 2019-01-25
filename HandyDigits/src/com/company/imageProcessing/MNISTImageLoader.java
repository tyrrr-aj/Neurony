package com.company.imageProcessing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.company.imageProcessing.ColorStyle.GreyScale;

public class MNISTImageLoader implements IImageLoader {
    private int[][] images;
    private int imageSize;

    public MNISTImageLoader () {
        this.imageSize = 28 * 28; // convention
    }

    public void loadImages(Path imagePath) {
        try {
            byte[] imageVector = Files.readAllBytes(imagePath);
            images = new int[imageVector.length / imageSize][imageSize];
            for (int i = 0; i < imageVector.length; i++) {
                images[i / imageSize][i % imageSize] = (int) imageVector[i];
            }
        }
        catch (IOException e) {
            System.out.println("No such file: " + imagePath);
        }
    }

    public float[][] getImages(ColorStyle colorStyle) {
        float[][] greyScaleImage = new float[images.length][imageSize];
        for (int i = 0; i < images.length; i++) {
            for (int j = 0; j < imageSize; j++) {
                greyScaleImage[i][j] = (float) 1.0 / (images[i][j] + 1); // +1 to avoid division by 0
            }
        }
        return (colorStyle == GreyScale) ? greyScaleImage : convertToBlackAndWhite(greyScaleImage);
    }

    private float[][] convertToBlackAndWhite(float[][] greyScaleImages) {
        float[][] blackAndWhiteImage = new float[greyScaleImages.length][imageSize];
        for (int i = 0; i < greyScaleImages.length; i++) {
            for (int j = 0; j < imageSize; j++) {
                blackAndWhiteImage[i][j] = (greyScaleImages[i][j] > 0.5) ? 1 : 0;
            }
        }
        return blackAndWhiteImage;
    }
}
