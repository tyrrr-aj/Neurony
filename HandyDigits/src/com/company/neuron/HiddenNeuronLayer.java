package com.company.neuron;

class HiddenNeuronLayer extends NeuronLayer{

    HiddenNeuronLayer(int numberOfNeurons, NeuronLayer prevLayer, float learningSpeed) {
        super(prevLayer, learningSpeed);
        this.neurons = new HiddenNeuron[numberOfNeurons];
        layerInput = new float[numberOfNeurons][prevLayer.getNumberOfNeurons() + 1]; // +1 for dummy neuron, always returning 1
        for (float[] neuronInput : layerInput) {
            neuronInput[neuronInput.length - 1] = 1; // dummy neuron's layerOutput
        }
        layerInputError = new float[prevLayer.getNumberOfNeurons()][numberOfNeurons];
    }

    void wireNextLayer(NeuronLayer nextLayer) {
        this.nextLayer = nextLayer;
        for (int i = 0; i < neurons.length; i++) {
            neurons[i] = new HiddenNeuron(i, layerInput[i], nextLayer.layerInput, layerInputError, learningSpeed);
        }
    }
}
