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
    private boolean isClearable;

    public Input(String label, List<Constraint> constraints) {
        this(label, constraints, true);
    }

    public Input(String label, List<Constraint> constraints, boolean isClearable) {
        this.label = label;
        this.constraints = constraints;
        this.isClearable = isClearable;
    }

    public String getLabel() {
        return label;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public boolean isClearable() {
        return isClearable;
    }
}
