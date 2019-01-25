package com.company.neuron;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Math.sqrt;

public class OutputNeuronLayer extends NeuronLayer {
    float[] networkOutput;
    float[][] networkError;

    OutputNeuronLayer(int numberOfNeurons, NeuronLayer prevLayer, float learningSpeed) {
        super(prevLayer, learningSpeed);
        layerInput = new float[numberOfNeurons][prevLayer.getNumberOfNeurons() + 1]; // +1 for dummy neuron, unused but expected by previous layer
        layerInputError = new float[prevLayer.getNumberOfNeurons()][numberOfNeurons];
        networkOutput = new float[numberOfNeurons];
        networkError = new float[numberOfNeurons][1];
        neurons = new OutputNeuron[numberOfNeurons];
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new OutputNeuron(i, layerInput[i], networkOutput, layerInputError, learningSpeed);
        }
    }

    void adjustWeightsAndPropagateError() {
        for (int i = 0; i < neurons.length; i++) {
            neurons[i].adjustWeightsAndPropagateError(networkError[i]);
        }
    }

    void setOutputError(float[] networkError) {
        for (int i = 0; i < networkError.length; i++) {
            this.networkError[i][0] = networkError[i];
        }
    }

    float[] getNetworkOutput() {
        return networkOutput;
    }

    float calculateInitialWeightsSpreadRadius() {
        return (float) sqrt(2 * 6.0 / (prevLayer.getNumberOfNeurons() + networkOutput.length));
    }

    void wireNextLayer(NeuronLayer nextLayer) {}
}
