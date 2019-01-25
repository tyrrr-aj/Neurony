package com.company.neuron;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HiddenNeuronTest {
    HiddenNeuron hiddenNeuron;
    float[] mockInput;
    float[][] mockOutput;
    float[][] mockLayerInputError;


    @BeforeAll
    void init() {
        hiddenNeuron.weights = new float[3];
        mockOutput = new float[5][1];
        mockInput = new float[3];
        hiddenNeuron = new HiddenNeuron(0, mockInput, mockOutput, mockLayerInputError, (float) 0.01);
    }

    @BeforeEach
    void setInitialValues() {
        hiddenNeuron.weights[0] = -2;
        hiddenNeuron.weights[1] = 0;
        hiddenNeuron.weights[2] = (float) 2.5;
        mockInput[0] = 1;
        mockInput[1] = 2;
        mockInput[2] = -3;
    }

    @Test
    void testActivationFunction() {
        assertEquals(0, hiddenNeuron.activationFunction(0));
        assertEquals(4.2, hiddenNeuron.activationFunction((float) 4.2));
        assertEquals(-1, hiddenNeuron.activationFunction((float) -0.2));
    }

    @Test
    void testActivationFunctionDerivative() {
        assertEquals(0, hiddenNeuron.activationFunctionDerivative(0, 0));
        assertEquals(2, hiddenNeuron.activationFunctionDerivative(1, 2));
        assertEquals(-0.25, hiddenNeuron.activationFunctionDerivative(-4, (float)0.5));
        assertEquals(-1, hiddenNeuron.activationFunctionDerivative(1, -1));
    }

    @Test
    void testRun() {
        float expectedResult = (float) 1.9;
        float[][] expectedOutput = new float[5][3];
        expectedOutput[0][0] = expectedResult;
        expectedOutput[1][0] = expectedResult;
        expectedOutput[2][0] = expectedResult;
        expectedOutput[3][0] = expectedResult;
        expectedOutput[4][0] = expectedResult;
        hiddenNeuron.run();
        assertArrayEquals(expectedOutput, mockOutput);
    }

    @Test
    public void testAdjustWeight() {
        hiddenNeuron.adjustWeight(0, (float)0.25);
        assertEquals(-0.005, hiddenNeuron.weights[0]);
    }

}