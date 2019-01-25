package com.company.imageProcessing;

import java.nio.file.Path;

public interface IImageLoader {
    public void loadImages(Path imagePath);

    public float[][] getImages(ColorStyle colorStyle);
}
