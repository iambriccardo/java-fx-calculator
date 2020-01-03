package com.riccardobusetti.calculator.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Enum that describes a computation that can be performed
 * by the application.
 *
 * @author riccardobusetti
 */
public enum Computation {
    ERATOSTHENES(
            "Eratosthenes algorithm",
            Collections.singletonList(
                    new Input("Insert n: ", Collections.singletonList(
                            Constraint.GREATER_THAN_1
                    ), true, true)
            ),
            false),
    GCD("Greatest common divisor",
            Arrays.asList(
                    new Input("Insert n: ", Collections.singletonList(
                            Constraint.GREATER_THAN_0
                    ), true, true),
                    new Input("Insert m: ", Collections.singletonList(
                            Constraint.GREATER_THAN_0
                    ), true, true)
            ), false),
    PRIME_NUMBERS(
            "Prime numbers",
            Collections.singletonList(
                    new Input("Insert n: ", Collections.singletonList(
                            Constraint.GREATER_THAN_0
                    ), true, true)
            ),
            false),
    COPRIME_NUMBERS(
            "Coprime numbers",
            Collections.singletonList(
                    new Input("Insert n: ", Collections.singletonList(
                            Constraint.GREATER_THAN_1
                    ), true, true)
            ),
            false),
    PRIME_FACTORIZATION(
            "Prime factorization",
            Collections.singletonList(
                    new Input("Insert n: ", Collections.singletonList(
                            Constraint.GREATER_THAN_1
                    ), true, true)
            ),
            false),
    SIGMA(
            "Sigma function",
            Arrays.asList(
                    new Input("Insert x: ", Collections.singletonList(
                            Constraint.GREATER_OR_EQUAL_THAN_0
                    ), true, true),
                    new Input("Insert n: ", Collections.singletonList(
                            Constraint.GREATER_THAN_1
                    ), true, true)
            ),
            false),
    LINEAR_CONGRUENTIAL_GENERATOR(
            "Linear congruential generator",
            Arrays.asList(
                    new Input("Insert a: ", Collections.emptyList(), true, true),
                    new Input("Insert b: ", Collections.emptyList(), true, true),
                    new Input("Insert m: ", Collections.emptyList(), true, true),
                    new Input("Insert n: ", Collections.singletonList(
                            Constraint.GREATER_THAN_0
                    ), true, true)
            ),
            false),
    PARTITION(
            "Partition function",
            Collections.singletonList(
                    new Input("Insert n: ", Collections.singletonList(
                            Constraint.GREATER_THAN_1
                    ), true, true)
            ),
            false);

    private String label;
    private List<Input> inputs;
    private boolean hasGraph;

    Computation(String label, List<Input> inputs, boolean hasGraph) {
        this.label = label;
        this.inputs = inputs;
        this.hasGraph = hasGraph;
    }

    public String getLabel() {
        return label;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public boolean hasGraph() {
        return hasGraph;
    }
}
