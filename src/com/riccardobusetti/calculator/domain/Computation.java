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
    ERATOSTHENES_ALGORITHM(
            "Eratosthenes algorithm",
            "This algorithm will compute all the prime numbers smaller or equal than n > 1.",
            Collections.singletonList(
                    new Input("Insert n: ", Collections.singletonList(
                            Constraint.GREATER_THAN_1
                    ))
            ),
            false),
    GCD("Greatest common divisor",
            "This algorithm will compute the greatest common divisor between n > 0 and m > 0.",
            Arrays.asList(
                    new Input("Insert n: ", Collections.singletonList(
                            Constraint.GREATER_THAN_0
                    ), true),
                    new Input("Insert m: ", Collections.singletonList(
                            Constraint.GREATER_THAN_0
                    ))
            ), false),
    PRIME_NUMBERS_NUMBER(
            "Prime numbers number",
            "This algorithm will compute the number of prime numbers smaller or equal than n > 0.",
            Collections.singletonList(
                    new Input("Insert n: ", Collections.singletonList(
                            Constraint.GREATER_THAN_0
                    ))
            ),
            true),
    EULERS_TOTIENT(
            "Euler's totient",
            "This algorithm will compute the number of positive integers that are coprime with n > 0.",
            Collections.singletonList(
                    new Input("Insert n: ", Collections.singletonList(
                            Constraint.GREATER_THAN_0
                    ))
            ),
            true),
    PRIME_FACTORIZATION(
            "Prime factorization",
            "This algorithm will compute the prime factorization of n > 1.",
            Collections.singletonList(
                    new Input("Insert n: ", Collections.singletonList(
                            Constraint.GREATER_THAN_1
                    ))
            ),
            false),
    SIGMA_FUNCTION(
            "Sigma function",
            "This algorithm will compute the sigma function with x >= 0 and n > 1.",
            Arrays.asList(
                    new Input("Insert x: ", Collections.singletonList(
                            Constraint.GREATER_OR_EQUAL_THAN_0
                    )),
                    new Input("Insert n: ", Collections.singletonList(
                            Constraint.GREATER_THAN_1
                    ))
            ),
            false),
    LINEAR_CONGRUENTIAL_GENERATOR(
            "Linear congruential generator",
            "This algorithm will compute the first n > 0 random numbers given four integers a, b, m with m > 0.",
            Arrays.asList(
                    new Input("Insert a: ", Collections.emptyList()),
                    new Input("Insert b: ", Collections.emptyList()),
                    new Input("Insert m: ", Collections.singletonList(
                            Constraint.GREATER_THAN_0
                    )),
                    new Input("Insert n: ", Collections.singletonList(
                            Constraint.GREATER_THAN_0
                    ))
            ),
            false),
    PARTITION_FUNCTION(
            "Partition function",
            "This algorithm will compute the partition function for n > 1.",
            Collections.singletonList(
                    new Input("Insert n: ", Collections.singletonList(
                            Constraint.GREATER_THAN_0
                    ))
            ),
            true);

    private String label;
    private String description;
    private List<Input> inputs;
    private boolean hasGraph;

    Computation(String label, String description, List<Input> inputs, boolean hasGraph) {
        this.label = label;
        this.description = description;
        this.inputs = inputs;
        this.hasGraph = hasGraph;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public boolean hasGraph() {
        return hasGraph;
    }
}
