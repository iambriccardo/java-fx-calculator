package com.riccardobusetti.calculator.exception;

/**
 * Class representing an exception that will be thrown in case
 * the input is not valid.
 *
 * @author riccardobusetti
 */
public class InvalidInputException extends Exception {

    /**
	 * serialVersionUID facilitates versioning of serialized data. 
	 * Its value is stored with the data when serializing. 
	 * When de-serializing, the same version is checked to see how the serialized data matches the current code.
	 */
	private static final long serialVersionUID = -4634694263266416014L;

	public InvalidInputException(String message) {
        super(message);
    }
}
