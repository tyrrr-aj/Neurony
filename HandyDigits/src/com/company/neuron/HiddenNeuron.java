package com.company.neuron;

import static java.lang.Float.max;

class HiddenNeuron extends Neuron{

    HiddenNeuron(int neuronID, float[] input, float[][] layerOutput, float[][] layerInputError, float learningSpeed) {
        super(neuronID, input, layerOutput, layerInputError, learningSpeed);
    }

    float activationFunction(float x) {
        return max((float) 0.2*x, x); // leaky ReLU function
    }

    float activationFunctionDerivative(float argVal, float argWeight) {
        return (float) ((argVal * argWeight < 0) ? 0.2 * argWeight : argWeight);
    }
}
