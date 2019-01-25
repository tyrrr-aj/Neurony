package com.company.imageProcessing;

import java.util.LinkedList;
import java.util.List;

public class TaggedImages {
    float[][] imageVectors;
    int label;

    public TaggedImages(float[][] imageVectors, int label) {
        this.imageVectors = imageVectors;
        this.label = label;
    }

    public float[][] getImageVectors() {
        return imageVectors;
    }

    public int getLabel() {
        return label;
    }
}
