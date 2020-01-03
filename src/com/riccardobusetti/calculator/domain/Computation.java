package com.riccardobusetti.calculator.domain;

import com.riccardobusetti.calculator.util.ListUtil;

import java.util.List;

/**
 * Enum that describes a computation that can be performed
 * by the application.
 *
 * @author riccardobusetti
 */
public enum Computation {
    ERATOSTHENES(1, ListUtil.listOf(
            new Input("Insert n: ", ListUtil.listOf(
                    Constraint.SMALL_OR_EQUAL_1
            ), true, true)
    ), false);

    private int id;
    private List<Input> inputs;
    private boolean hasGraph;

    Computation(int id, List<Input> inputs, boolean hasGraph) {
        this.id = id;
        this.inputs = inputs;
        this.hasGraph = hasGraph;
    }
}
