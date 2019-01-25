package com.company.neuron;


public class NeuralNetwork {
    private NeuronLayer[] neuronLayers;
    private SummaryFunction summaryFunction;

    public NeuralNetwork(int inputSize, int numberOfOutputClasses, int numberOfHiddenLayers, int[] numbersOfNeuronsInHiddenLayers, float baseLearningSpeed) {
        neuronLayers = new NeuronLayer[numberOfHiddenLayers + 2];
        neuronLayers[0] = new InputNeuronLayer(inputSize);
        for (int i = 1; i < neuronLayers.length - 1; i++) {
            neuronLayers[i] = new HiddenNeuronLayer(numbersOfNeuronsInHiddenLayers[i-1], neuronLayers[i-1], baseLearningSpeed);
        }
        neuronLayers[neuronLayers.length - 1] = new OutputNeuronLayer(numberOfOutputClasses, neuronLayers[numberOfHiddenLayers - 1], baseLearningSpeed);
        for (int i = 0; i < neuronLayers.length - 1; i++) {
            neuronLayers[i].wireNextLayer(neuronLayers[i+1]);
        }
        summaryFunction = new SummaryFunction((OutputNeuronLayer) neuronLayers[neuronLayers.length - 1]);
    }

    public int classify(float[] data) {
        InputNeuronLayer inputNeuronLayer = (InputNeuronLayer) neuronLayers[0];
        inputNeuronLayer.setInput(data);
        for (NeuronLayer layer : neuronLayers) {
            layer.activateLayer();
        }
        return summaryFunction.getSingleResult();
    }

    public void learn(float[][] data, int[] properResults) {
        System.out.println("Learning new stuff!");
        float[] netResults;
        for (int i = 0; i < properResults.length; i++) {
            netResults = classifyAndGetAllResults(data[i]);
            learnFromExample(netResults, properResults[i]);
        }
    }

    private void learnFromExample(float netResults[], int properResult) {
        OutputNeuronLayer outputNeuronLayer = (OutputNeuronLayer) neuronLayers[neuronLayers.length - 1];
        outputNeuronLayer.setOutputError(calculateLastLayerError(netResults, properResult));
        for (int i = neuronLayers.length - 1; i > 0; i--) { // >0 because input layer does not learn
            neuronLayers[i].adjustWeightsAndPropagateError();
        }
    }

    public void setPredefinedWeights() {
        for (NeuronLayer neuronLayer : neuronLayers) {
            neuronLayer.setWeights();
        }
    }

    private float[] classifyAndGetAllResults(float[] data) {
        InputNeuronLayer inputNeuronLayer = (InputNeuronLayer) neuronLayers[0];
        inputNeuronLayer.setInput(data);
        for (NeuronLayer layer : neuronLayers) {
            layer.activateLayer();
        }
        return summaryFunction.getClassesResults();
    }

    private static float[] calculateLastLayerError(float[] netResults, int properResult) {
        float[] lastLayerError = new float[netResults.length];
        for (int i = 0; i < netResults.length; i++) {
            lastLayerError[i] = (i == properResult) ? 1 - netResults[i] : 0 - netResults[i];
        }
        return lastLayerError;
    }

    public int getNumberOfHiddenLayers() {
        return neuronLayers.length - 2;
    }

    public int[] getNumberOfNeuronsInHiddenLayers() {
        int[] numberOfNeuronsInHiddenLayers = new int[neuronLayers.length - 2];
        for (int i = 0; i < numberOfNeuronsInHiddenLayers.length; i++) {
            numberOfNeuronsInHiddenLayers[i] = neuronLayers[i + 1].getNumberOfNeurons();
        }
        return numberOfNeuronsInHiddenLayers;
    }

    public float getLearningSpeed() {
        return neuronLayers[1].learningSpeed;
    }
}
