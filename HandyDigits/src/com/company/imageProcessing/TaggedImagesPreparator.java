package com.company.imageProcessing;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TaggedImagesPreparator {
    private List<TaggedImages> learningData;
    private IImageLoader imageLoader;
    private Path sourceDirectoryPath;
    private ColorStyle colorStyle;

    public TaggedImagesPreparator(Path sourceDirectoryPath, IImageLoader imageLoader, ColorStyle colorStyle) throws IOException {
        if (!Files.isDirectory(sourceDirectoryPath)) {
            throw new IOException("Illegal path (it's not a directory): " + sourceDirectoryPath);
        }
        this.sourceDirectoryPath = sourceDirectoryPath;
        this.imageLoader = imageLoader;
        learningData = new LinkedList<>();
    }

    public List<TaggedImages> getLearningData() throws IOException{
        List<Path> files = getFiles();
        imageLoader = new MNISTImageLoader();
        for (Path file : files) {
            imageLoader.loadImages(file);
            learningData.add(new TaggedImages(imageLoader.getImages(colorStyle), getLabel(file)));
        }
        return learningData;
    }

    private List<Path> getFiles() throws IOException {
        List<Path> files = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDirectoryPath, "data*")) {
            for (Path entry: stream) {
                files.add(entry);
            }
        } catch (DirectoryIteratorException ex) {
            throw ex.getCause();
        }
        return files;
    }

    private int getLabel(Path file) {
        String filename = file.getFileName().toString();
        return Integer.parseInt(filename.replace("data", ""));
    }
}
