package com.company.neuron;

import java.util.Arrays;

public class InputNeuron extends HiddenNeuron {


    InputNeuron(int neuronID, float[] input, float[][] layerOutput) {
        super(neuronID, input, layerOutput, null, 0);
    }

    float activationFunction(float x) {
        return x; // identity function
    }

    float activationFunctionDerivative(float argVal, float argWeight) {
        return argWeight;
    }

    void adjustWeightsAndPropagateError(float neuronErrorComponents[]) {}

    void setWeights() {
        weights = new float[input.length];
        Arrays.fill(weights, 1);
    }

    void adjustWeight(int index, float neuronError) {}


}
