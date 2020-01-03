package com.riccardobusetti.calculator.exception;

/**
 * Class representing an exception that will be thrown in case
 * the input is not valid.
 *
 * @author riccardobusetti
 */
public class InvalidInputException extends Exception {

    public InvalidInputException(String message) {
        super(message);
    }
}
