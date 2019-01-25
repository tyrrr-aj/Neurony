package com.company.UI;

import asg.cliche.Command;
import asg.cliche.ShellFactory;

import com.company.imageProcessing.ImageToProcess;
import com.company.imageProcessing.MNISTImageLoader;
import com.company.imageProcessing.TaggedImages;
import com.company.imageProcessing.TaggedImagesPreparator;
import com.company.neuron.NeuralNetwork;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.company.imageProcessing.ColorStyle.BlackAndWhite;
import static java.lang.Integer.min;

public class Shell {
    private Path learningDataSourceDirectoryPath;
    private NeuralNetwork neuralNetwork;
    private int maxNumberOfImages = 1000;

    public Shell() throws IOException {
        ShellFactory.createConsoleShell("do", "HandyDigits> ", this).commandLoop();
    }

    @Command
    public void createNueralNetwork() {
        int[] numberOfNeuronsInHiddenayers = {150, 150};
        neuralNetwork = new NeuralNetwork(28*28,10, 2, numberOfNeuronsInHiddenayers, (float) 0.001);
    }

    @Command
    public void createCustomNeuralNetwork(int inputSize, int numberOfOutputClasses, int numberOfHiddenLayers, int numberOfNeuronsPerHiddenLayer,
                                          float baseLearningSpeed) {
        int[] numberOfNeuronsInHiddenLayers = new int[numberOfHiddenLayers];
        for (int i = 0; i < numberOfHiddenLayers; i++) {
            numberOfNeuronsInHiddenLayers[i] = numberOfNeuronsPerHiddenLayer;
        }
        neuralNetwork = new NeuralNetwork(inputSize, numberOfOutputClasses, numberOfHiddenLayers, numberOfNeuronsInHiddenLayers, baseLearningSpeed);
    }

    @Command
    public void specifyLearningDataSource(String sourceDirectory) {
        Path sourceDirectoryPath = Paths.get(sourceDirectory);
        if (Files.isDirectory(sourceDirectoryPath)) {
            learningDataSourceDirectoryPath = sourceDirectoryPath;
        }
        else {
            System.out.println("Wrong path: " + sourceDirectory + " is not a directory");
        }
    }

    @Command
    public void startLearning() {
        neuralNetwork.setPredefinedWeights();
        train();
    }

    @Command
    public void train() {
        MNISTImageLoader loader = new MNISTImageLoader();
        try {
            TaggedImagesPreparator preparator = new TaggedImagesPreparator(learningDataSourceDirectoryPath, loader, BlackAndWhite);
            List<TaggedImages> taggedImages = preparator.getLearningData();
            for (TaggedImages imageBundle : taggedImages) {
                int[] modelResults = new int[min(imageBundle.getImageVectors().length, maxNumberOfImages)];
                for (int i = 0; i < modelResults.length; i++) {
                    modelResults[i] = imageBundle.getLabel();
                }
                neuralNetwork.learn(imageBundle.getImageVectors(), modelResults);
            }
        }
        catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    @Command
    public void classify(String imagePath) {
        try {
            ImageToProcess image = new ImageToProcess(imagePath);
            image = image.trimAndResize(28, 28);
            System.out.println(neuralNetwork.classify(image.convertImageToBlackAndWhite()));
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Command
    public void setImagesLimit(int maxNumberOfImages) {
        this.maxNumberOfImages = maxNumberOfImages;
    }

    @Command
    public void displayHyperparams() {
        System.out.println("Number of hidden layers: " + neuralNetwork.getNumberOfHiddenLayers());
        System.out.print("Number of neurons in hidden layers: ");
        for (int num : neuralNetwork.getNumberOfNeuronsInHiddenLayers()) {
            System.out.print(num + " ");
        }
        System.out.println();
        System.out.println("Learning speed: " + neuralNetwork.getLearningSpeed());
        System.out.println(maxNumberOfImages);
    }
}
