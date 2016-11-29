package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        InferenceEngine ie = new InferenceEngine("rulebase.txt", "goals.txt");
        ie.recursiveInferenceAlgorithm("(bg)", "");


    }
}
