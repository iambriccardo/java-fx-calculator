package com.riccardobusetti.calculator.ui.custom;

import com.riccardobusetti.calculator.domain.Constraint;
import javafx.scene.control.TextField;

import java.util.List;

public class ValidatableTextField extends TextField {

    private List<Constraint> constraints;

    public ValidatableTextField(String placeHolder, List<Constraint> constraints, boolean isMandatory, boolean isClearable) {
        super("");
        setPromptText(placeHolder);
        this.constraints = constraints;
    }

    public Integer getValue() {
        Integer value = getInteger();

        if (value == null) {
            showError("You must insert an integer number");
        } else {
            String violationMessage = validateInput(value);

            if (violationMessage != null) {
                showError(violationMessage);
                value = null;
            }
        }

        return value;
    }

    private String validateInput(Integer value) {
        StringBuilder violationMessage = null;

        for (Constraint constraint: constraints) {
            if (!constraint.isValid(value)) {
                violationMessage = (violationMessage == null ? new StringBuilder("null") : violationMessage).append(constraint.getViolationMessage()).append("\n");
            }
        }

        return violationMessage == null ? null : violationMessage.toString();
    }

    private Integer getInteger() {
        try {
            return Integer.parseInt(getText());
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private void showError(String error) {

    }
}
