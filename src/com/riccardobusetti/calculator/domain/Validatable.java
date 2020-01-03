package com.riccardobusetti.calculator.domain;

/**
 * Interface representing an element which can be validated.
 *
 * @param <T> type of the element that will be validated.
 *
 * @author riccardobusetti
 */
public interface Validatable<T> {

    /**
     * Checks if the element is valid.
     *
     * @param value value of the element.
     * @return true if it valid, false otherwise.
     */
    boolean isValid(T value);
}
