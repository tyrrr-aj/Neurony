package com.company.neuron;

import java.util.Arrays;

class InputNeuronLayer extends NeuronLayer{

    InputNeuronLayer(int inputSize) {
        super(null, 0);
        neurons = new InputNeuron[inputSize];
        layerInput = new float[inputSize + 1][1]; // +1 for dummy neuron, always returning 1
        layerInput[inputSize][0] = 1; // dummy neuron
    }

    void setInput(float[] data) {
        for (int i = 0; i < data.length; i++) {
            layerInput[i][0] = data[0];
        }
    }

    void wireNextLayer(NeuronLayer firstHiddenLayer) {
        nextLayer = firstHiddenLayer;
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new InputNeuron(i, layerInput[i], nextLayer.layerInput);
        }
    }

    void setWeights(float[][] weights) {}

    void setWeights() {
        for (Neuron neuron : neurons) {
            InputNeuron inputNeuron = (InputNeuron) neuron;
            inputNeuron.setWeights();
        }
    }

    void adjustWeightsAndPropagateError() {}
}
