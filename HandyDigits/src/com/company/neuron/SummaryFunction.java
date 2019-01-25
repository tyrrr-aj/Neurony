package com.company.neuron;

import static java.lang.Math.exp;

class SummaryFunction { // implemented as softmax function
    OutputNeuronLayer outputLayer;

    SummaryFunction(OutputNeuronLayer outputLayer) {
        this.outputLayer = outputLayer;
    }

    int getSingleResult() {
        float[] classesScore = getClassesResults();
        return findMaxElementIndex(classesScore);
    }

    float[] getClassesResults() {
        float[] score = outputLayer.getNetworkOutput();
        for (int i = 0; i < score.length; i++) {
            score[i] = (float) exp(score[i]);
        }
        float sumOfExponents = sumArrayElements(score);
        for (int i = 0; i < score.length; i++) {
            score[i] /= sumOfExponents;
        }
        return score;
    }

    private int findMaxElementIndex(float[] arr) {
        int indexOfMax = 0;
        for (int i = 1; i < arr.length; i++) {
            indexOfMax = (arr[i] > arr[indexOfMax]) ? i : indexOfMax;
        }
        return indexOfMax;
    }

    private float sumArrayElements(float[] arr) {
        float sum = 0;
        for (float e : arr) {
            sum += e;
        }
        return sum;
    }
}
