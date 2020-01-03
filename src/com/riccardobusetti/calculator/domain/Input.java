package com.riccardobusetti.calculator.domain;

import java.util.List;

/**
 * Class that represents an input field inside of the application.
 *
 * @author riccardobusetti
 */
public class Input {

    private String label;
    private List<Constraint> constraints;
    private boolean isMandatory;
    private boolean isClearable;

    public Input(String label, List<Constraint> constraints, boolean isMandatory, boolean isClearable) {
        this.label = label;
        this.constraints = constraints;
        this.isMandatory = isMandatory;
        this.isClearable = isClearable;
    }

    public String getLabel() {
        return label;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public boolean isClearable() {
        return isClearable;
    }
}
