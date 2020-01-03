package com.riccardobusetti.calculator.domain;

import java.util.Arrays;
import java.util.List;

/**
 * Enum that describes a computation that can be performed
 * by the application.
 *
 * @author riccardobusetti
 */
public enum Computation {
    ERATOSTHENES(1,
            "Eratosthenes function",
            Arrays.asList(
                    new Input("Insert n: ", Arrays.asList(
                            Constraint.GREATER_THAN_1
                    ), true, true)
            ),
            false),
    GCD(2, "Greatest common divisor",
            Arrays.asList(
                    new Input("Insert a: ", Arrays.asList(
                            Constraint.GREATER_THAN_0
                    ), true, true),
                    new Input("Insert b: ", Arrays.asList(
                            Constraint.GREATER_THAN_0
                    ), true, true)
            ), false);

    private int id;
    private String label;
    private List<Input> inputs;
    private boolean hasGraph;

    Computation(int id, String label, List<Input> inputs, boolean hasGraph) {
        this.id = id;
        this.label = label;
        this.inputs = inputs;
        this.hasGraph = hasGraph;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public boolean isHasGraph() {
        return hasGraph;
    }
}
