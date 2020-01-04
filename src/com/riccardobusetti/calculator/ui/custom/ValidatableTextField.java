package com.riccardobusetti.calculator.ui.custom;

import com.riccardobusetti.calculator.domain.Constraint;
import com.riccardobusetti.calculator.exception.InvalidInputException;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom textfield that is able to validate a specific input given some constraints.
 *
 * @author riccardobusetti
 */
public class ValidatableTextField extends TextField {

    private List<Constraint> constraints;

    public ValidatableTextField(List<Constraint> constraints, boolean isMandatory, boolean isClearable) {
        super("");
        this.constraints = constraints;
    }

    public Integer getIntegerValue() {
        return hasInteger();
    }

    public Integer validate() throws InvalidInputException {
        Integer value = hasInteger();

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
        List<String> violationMessages = new ArrayList<>();

        for (Constraint constraint : constraints) {
            if (!constraint.isValid(value)) {
                violationMessages.add(constraint.getViolationMessage());
            }
        }

        return violationMessages.size() > 0 ?
                violationMessages.stream().map(String::valueOf).collect(Collectors.joining("-")) :
                null;
    }

    private Integer hasInteger() {
        try {
            return Integer.parseInt(getText());
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}
