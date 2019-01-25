package com.company.neuron;

public abstract class Neuron implements Runnable{
    int neuronID;
    float[] input;
    float[] weights;
    float[][] layerOutput; // field of NeuronLayer containing neuron (accessed directly for efficient parallel computation)
    float[][] layerInputError; // field of NeuronLayer containing neuron (accessed directly for efficient parallel computation)
    float learningSpeed;

    Neuron(int neuronID, float[] input, float[][] layerOutput, float[][] layerInputError, float learningSpeed) {
        this.neuronID = neuronID;
        this.input = input;
        this.layerOutput = layerOutput;
        this.layerInputError = layerInputError;
        this.learningSpeed = learningSpeed;
    }

    public void run() { // forward work - calculating neuron layerOutput based on input
        float sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += input[i] * weights[i];
        }
        float result = activationFunction(sum);
        for (int i = 0; i < layerOutput.length - 1; i++) { // -1 is for dummy neuron at the end, always returning ones
            layerOutput[i][neuronID] = result;
        }
    }

    void adjustWeightsAndPropagateError(float neuronErrorComponents[]) { // backward work - adjusting weights on input and propagating error based on neuron layerOutput error
        float neuronError = sumArray(neuronErrorComponents);
        for (int i = 0; i < input.length - 1; i++) { // -1 for ignoring dummy neuron
            layerInputError[i][neuronID] = neuronError * weights[i];
            adjustWeight(i, neuronError);
        }
        adjustWeight(input.length - 1, neuronError); // adjusting weight of dummy neuron's layerOutput
    }

    void setWeights(float[] weights) {
        this.weights = weights;
    }

    void adjustWeight(int index, float neuronError) {
        weights[index] += learningSpeed * neuronError * activationFunctionDerivative(input[index], weights[index]);
    }

    abstract float activationFunction(float x);

    abstract float activationFunctionDerivative(float argVal, float argWeight);

    float sumArray(float[] arr) {
        float sum = 0;
        for (float e : arr) {
            sum += e;
        }
        return sum;
    }
}
