package com.riccardobusetti.calculator.ui.custom;

import com.riccardobusetti.calculator.domain.Constraint;
import com.riccardobusetti.calculator.exception.InvalidInputException;
import javafx.scene.control.TextField;

import java.util.List;

public class ValidatableTextField extends TextField {

    private List<Constraint> constraints;

    public ValidatableTextField(List<Constraint> constraints, boolean isMandatory, boolean isClearable) {
        super("");
        this.constraints = constraints;
    }

    public Integer getIntegerValue() {
        return getInteger();
    }

    public Integer validate() throws InvalidInputException {
        Integer value = getInteger();

        System.out.println("Input value is " + value);

        if (value == null) {
            throw new InvalidInputException("You must insert an integer number");
        } else {
            String violationMessage = checkConstraints(value);

            if (violationMessage != null) {
                throw new InvalidInputException(violationMessage);
            }
        }

        return value;
    }

    private String checkConstraints(Integer value) {
        StringBuilder violationMessage = new StringBuilder();

        for (Constraint constraint: constraints) {
            if (!constraint.isValid(value)) {
                violationMessage.append(constraint.getViolationMessage()).append(" ");
            }
        }

        return violationMessage.toString().isEmpty() ? null : violationMessage.toString();
    }

    private Integer getInteger() {
        try {
            return Integer.parseInt(getText());
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}
