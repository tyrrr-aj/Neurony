package com.company.neuron;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Math.sqrt;

public abstract class NeuronLayer {
    Neuron[] neurons;
    NeuronLayer prevLayer;
    NeuronLayer nextLayer;
    float[][] layerInput; // [neuron from this layer][neuron's input], this field is accessed directly by neurons from previous layer, so it must stay package-private
    float[][] layerInputError; // [neuron from previous layer][neuron's error components], this field is accessed directly by neurons from previous layer, so it must stay package-private
    float learningSpeed;

    NeuronLayer(NeuronLayer prevLayer, float learningSpeed) {
        this.prevLayer = prevLayer;
        this.learningSpeed = learningSpeed;
    }

    abstract void wireNextLayer(NeuronLayer nextLayer);

    void activateLayer() {
        ExecutorService es = Executors.newFixedThreadPool(neurons.length);
        for (Neuron neuron : neurons) {
            es.execute(neuron);
        }
        es.shutdown();
        while (!es.isTerminated()); // we make sure all neurons of the layer have completed their computations
    }

    void adjustWeightsAndPropagateError() {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].adjustWeightsAndPropagateError(nextLayer.layerInputError[i]);
        }
    }

    void setWeights(float[][] weights) {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].setWeights(weights[i]);
        }
    }

    void setWeights() {
        float r = calculateInitialWeightsSpreadRadius();
        float smallStep = 2 * r / (neurons.length * prevLayer.getNumberOfNeurons() + 1); // +1 for dummy neuron
        float bigStep = 2 * r / neurons.length;
        for (int i = 0; i < neurons.length; i++) {
            float[] weights = new float[prevLayer.getNumberOfNeurons() + 1]; // +1 for dummy neuron
            for (int j = 0; j < weights.length; j++) {
                weights[j] = -r + i * bigStep + j * smallStep;
            }
            neurons[i].setWeights(weights);
        }
    }

    int getNumberOfNeurons() {
        return neurons.length;
    }

    float calculateInitialWeightsSpreadRadius() {
        return (float) sqrt(2 * 6.0 / (prevLayer.getNumberOfNeurons() + nextLayer.getNumberOfNeurons()));
    }

    public float getLearningSpeed() {
        return learningSpeed;
    }
}
