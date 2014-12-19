/*
 * Argus Open Source
 * Software to apply Statistical Disclosure Control techniques
 *
 * Copyright 2014 Statistics Netherlands
 *
 * This program is free software; you can redistribute it and/or 
 * modify it under the terms of the European Union Public Licence 
 * (EUPL) version 1.1, as published by the European Commission.
 *
 * You can find the text of the EUPL v1.1 on
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * This software is distributed on an "AS IS" basis without 
 * warranties or conditions of any kind, either express or implied.
 */
package argus.model;

import com.ibm.statistics.plugin.MeasurementLevel;
import com.ibm.statistics.plugin.VariableFormat;
import java.util.Map;

/**
 *
 * @author pibd05
 */
public class SpssVariable {

    private final String name;
    private final MeasurementLevel measurementLevel; //e.g. scale, nominal, ordinal
    private int variableLength = 0;
    private int numberOfDecimals = 0;
    private boolean numeric = false;
    private boolean categorical = false;
    private double[] numericMissings;
    private String[] stringMissings;
    private final int variableType;
    private final String variableLabel;
    private final String[] variableAttributeNames;
    private final VariableFormat variableFormat;
    private Map<String, String> stringValueLabels;
    private Map<Double, String> numericValueLabels;

    private boolean selected; //indicated whether selected in Argus meta or not

    public SpssVariable(String name, int formatDecimal, int formatWidth, MeasurementLevel measurementLevel,
            int variableType, String variableLabel, String[] variableAttributeNames, VariableFormat variableFormat){//, Map<Double,String> numericValueLabels) {
        this.name = name;
        this.numberOfDecimals = formatDecimal;
        this.variableLength = formatWidth;
        this.measurementLevel = measurementLevel;
        this.variableType = variableType;
        this.variableLabel = variableLabel;
        this.variableAttributeNames = variableAttributeNames;
        this.variableFormat = variableFormat;
        //this.numericValueLabels = numericValueLabels;
        setMeasurementLevel();
    }

    private void setMeasurementLevel() {
        switch (measurementLevel.toString()) {
            case ("SCALE"):
                this.numeric = true;
                break;
            case ("ORDINAL"):
                this.categorical = true;
                this.numeric = true;
                break;
            case ("NOMINAL"):
                this.categorical = true;
                break;
            default:
                break;
        }
    }

    public String getName() {
        return name;
    }

    public MeasurementLevel getMeasurementLevel() {
        return measurementLevel;
    }

    public int getVariableLength() {
        return variableLength;
    }

    public int getNumberOfDecimals() {
        return numberOfDecimals;
    }

    public boolean isNumeric() {
        return numeric;
    }

    public boolean isCategorical() {
        return categorical;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getVariableType() {
        return variableType;
    }

    public String getVariableLabel() {
        return variableLabel;
    }

    public String[] getVariableAttributeNames() {
        return variableAttributeNames;
    }

    public VariableFormat getVariableFormat() {
        return variableFormat;
    }

//    public Map<Double,String> getNumericValueLabels() {
//        return numericValueLabels;
//    }
    public Map<String, String> getStringValueLabels() {
        return stringValueLabels;
    }

    public void setStringValueLabels(Map<String, String> stringValueLabels) {
        this.stringValueLabels = stringValueLabels;
    }

    public Map<Double, String> getNumericValueLabels() {
        return numericValueLabels;
    }

    public void setNumericValueLabels(Map<Double, String> numericValueLabels) {
        this.numericValueLabels = numericValueLabels;
    }

    public double[] getNumericMissings() {
        return numericMissings;
    }

    public void setNumericMissings(double[] numericMissings) {
        this.numericMissings = numericMissings;
    }

    public String[] getStringMissings() {
        return stringMissings;
    }

    public void setStringMissings(String[] stringMissings) {
        this.stringMissings = stringMissings;
    }
    
}
