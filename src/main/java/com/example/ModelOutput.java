package com.example;

public class ModelOutput {
    String className;
    String secondClassName;
    int probabilitySecondClassInPercent;
    int probabilityInPercent;

    ModelOutput( String className, double probabilityInDouble, String secondClassName, double probabilitySecondInDouble ) {
        this.className = className;
        this.secondClassName = secondClassName;
        this.probabilityInPercent = (int) Math.round(probabilityInDouble * 100);
        this.probabilitySecondClassInPercent = (int) Math.round(probabilitySecondInDouble * 100);
    }

    public String getClassName() {
        return className;
    }

    public int getProbabilityInPercent() {
        return probabilityInPercent;
    }

    public String getSecondClassName() {
        return secondClassName;
    }

    public int getProbabilitySecondClassInPercent() {
        return probabilitySecondClassInPercent;
    }
}
