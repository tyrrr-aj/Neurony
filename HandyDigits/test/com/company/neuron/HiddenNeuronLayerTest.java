package com.company.neuron;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HiddenNeuronLayerTest {
    private NeuronLayer thisLayer;
    private NeuronLayer prevLayer;
    private NeuronLayer nextLayer;

    @BeforeAll
    public void placeInNeighborhood() {
        prevLayer = new HiddenNeuronLayer(5, null, (float)0.01);
        thisLayer = new HiddenNeuronLayer(5, prevLayer, (float)0.01);
        nextLayer = new HiddenNeuronLayer(5, thisLayer, (float)0.01);
    }

    @Test
    public void testWireNextLayer() {
        thisLayer.wireNextLayer(nextLayer);
        assertSame(thisLayer.nextLayer, nextLayer);
        for (Neuron neuron : thisLayer.neurons) {
            assertSame(neuron.layerOutput, nextLayer.layerInput);
        }
    }

    @Test
    public void testActivateLayer() {
        nextLayer.layerInput = new float[2][6];
        thisLayer.wireNextLayer(nextLayer);
        thisLayer.layerInput = new float[5][3];
        thisLayer.neurons = new HiddenNeuron[5];
        for (int i = 0; i < 5; i++) {
            thisLayer.layerInput[i][0] = -1;
            thisLayer.layerInput[i][1] = 0;
            thisLayer.layerInput[i][2] = (float) 2.5;
            thisLayer.neurons[i] = new HiddenNeuron(0, thisLayer.layerInput[i], nextLayer.layerInput, thisLayer.layerInputError, (float) 0.01);
            thisLayer.neurons[i].weights = new float[3];
            for (int j = 0; j < 3; j++) {
                thisLayer.neurons[i].weights[j] = 1;
            }
        }
        for (int i = 0; i < 5; i++) {
            assertEquals((float) 1.5, nextLayer.layerInput[0][i]);
            assertEquals((float) 1.5, nextLayer.layerInput[1][i]);
        }
        assertEquals(1, nextLayer.layerInput[0][5]);
        assertEquals(1, nextLayer.layerInput[1][5]);
    }
}