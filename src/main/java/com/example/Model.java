package com.example;

import ai.onnxruntime.*;

import java.nio.FloatBuffer;
import java.util.Collections;
import java.util.Map;

public class Model {

    OrtEnvironment env;
    OrtSession session;

    final int inputSize = 12;

    Model( String modelPath ) throws OrtException {
        env = OrtEnvironment.getEnvironment();
        OrtSession.SessionOptions sessionOptions = new OrtSession.SessionOptions();
        session = env.createSession(modelPath, sessionOptions);
    }

    ModelOutput inference(float[] input) throws OrtException {

        double[] means = {0.5389188253801783, 0.6007724750917671, 5.844782380702674, -9.259075249082327, 0.6237545883586786, 0.07706879916098584, 0.33895585016780283, 0.19885853316465654, 0.18996908757210282, 0.46251227058206607, 120.73227975878342, 3.8922391190351338};
        double[] scale = {0.17199890140343907, 0.26494278780437297, 3.1549775544372127, 4.978758840606938, 0.4844427745979643, 0.08022783590948653, 0.36393622672693504, 0.3267586858383668, 0.15566709066550447, 0.2497960565024776, 30.22246122352673, 0.40922448235592596};

        for (int i = 0; i < input.length; ++i) {
            input[i] = (input[i] - (float)means[i])/(float)scale[i] ;
        }

        OnnxTensor inputTensor = OnnxTensor.createTensor(env, FloatBuffer.wrap(input), new long[]{1, inputSize});


        Map<String, OnnxTensor> inputs = Collections.singletonMap("input", inputTensor);
        OrtSession.Result results = session.run(inputs);

        String[] classNames = {"Acoustic/Folk", "Alt_Music", "Blues", "Bollywood", "Country", "HipHop", "Indie Alt", "Instrumental", "Metal", "Pop", "Rock"};

        OnnxValue outputValue = results.get(0);
        float[][] outputArray = (float[][]) outputValue.getValue();
        for (int i = 0; i < outputArray[0].length; i++) {
            System.out.println(classNames[i] + " " + String.format( "%.2f", outputArray[0][i] * 100 ) + " %" );
        }
 /*       for (float val : outputArray[0]) {
            System.out.println("Output value: " + val);
        }*/
        System.out.println("---------------------------");

        float maxValue = outputArray[0][0];
        int maxIndex = 0;
        int secondIndex = 0;

        for (int i = 1; i < outputArray[0].length; i++) {
            if (outputArray[0][i] > maxValue) {
                maxValue = outputArray[0][i];
                secondIndex = maxIndex;
                maxIndex = i;
            }
        }
        return new ModelOutput( classNames[ maxIndex ] , outputArray[0][maxIndex],classNames[ secondIndex ] , outputArray[0][secondIndex]  );
        //return classNames[ maxIndex ];
    }

}
