package com.company.neuron;

public class OutputNeuron extends HiddenNeuron {
    float[] networkOutput;

    public OutputNeuron(int neuronID, float[] input, float[] networkOutput, float[][] layerInputError, float learningSpeed) {
        super(neuronID, input, null, layerInputError, learningSpeed);
        this.networkOutput = networkOutput;
    }

    public void run() { // forward work - calculating neuron layerOutput based on input
        float sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += input[i] * weights[i];
        }
        networkOutput[neuronID] = sum;
    }

    float activationFunction(float x) {
        return x; // identity function
    }

    float activationFunctionDerivative(float argVal, float argWeight) {
        return argWeight;
    }
}