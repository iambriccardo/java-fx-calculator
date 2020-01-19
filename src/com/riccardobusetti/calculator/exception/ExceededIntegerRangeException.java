package com.riccardobusetti.calculator.exception;

/**
 * Class representing an exception that will be thrown if the result of computation
 * exceeds the Integer.MAX_VALUE range.
 *
 * @author riccardobusetti
 */
public class ExceededIntegerRangeException extends Exception {

    public ExceededIntegerRangeException() {
        super("The program works with 32bit integers and this computation exceeds the range.");
    }
}
