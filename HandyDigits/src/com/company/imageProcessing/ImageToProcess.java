package com.company.imageProcessing;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageToProcess {
    public BufferedImage image;

    public ImageToProcess(String imagePath) throws FileNotFoundException {
        try {
            image = ImageIO.read(new File(imagePath));
        }
        catch (IOException e) {
            throw new FileNotFoundException("No image at: " + imagePath);
        }
    }

    public ImageToProcess(BufferedImage image) {
        this.image = image;
    }

    public ImageToProcess trimAndResize(int xSize, int ySize) {
//        int scaledImageHeight = (xSize /image.getWidth()) * image.getHeight();
//        BufferedImage buffImage = (BufferedImage) image.getScaledInstance(xSize,scaledImageHeight, Image.SCALE_FAST);
        BufferedImage buffImage = image;// TODO: get rescaling (above) to work instead
        int xLUCorner = (buffImage.getWidth() - xSize) / 2;
        int yLUCorner = (buffImage.getHeight() - ySize) / 2;
        return new ImageToProcess(buffImage.getSubimage(xLUCorner, yLUCorner, xSize, ySize));
    }

    public float[] convertImageToBlackAndWhite() {
        BufferedImage BWRGBimage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        float[] BWimage = new float[BWRGBimage.getWidth() * BWRGBimage.getHeight()];
        for (int x = 0; x < BWRGBimage.getWidth(); x++) {
            for (int y = 0; y < BWRGBimage.getHeight(); y++) {
                BWimage[x * BWRGBimage.getHeight() + y] = (BWRGBimage.getRGB(x, y) == 0) ? 0 : 1;
            }
        }
        return BWimage;
    }
}
