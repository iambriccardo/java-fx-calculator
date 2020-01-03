package com.riccardobusetti.calculator.domain;

/**
 * Enum that represents a specific constraint that must be respected
 * in order for the {@link Computation} to complete successfully.
 *
 * @author riccardobusetti
 */
public enum Constraint implements Validatable<Integer> {
    GREATER_THAN_0(value -> value > 0, "The value must be > 0"),
    GREATER_THAN_1(value -> value > 1, "The value must be > 1");

    private Validatable<Integer> validatable;
    private String violationMessage;

    Constraint(Validatable<Integer> validatable, String violationMessage) {
        this.validatable = validatable;
        this.violationMessage = violationMessage;
    }

    public String getViolationMessage() {
        return violationMessage;
    }

    @Override
    public boolean isValid(Integer value) {
        return validatable.isValid(value);
    }
}
