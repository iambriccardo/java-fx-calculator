package com.riccardobusetti.calculator.ui.custom;

import com.riccardobusetti.calculator.domain.Constraint;
import com.riccardobusetti.calculator.exception.InvalidInputException;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * Custom layout which contains a textfield with multiple labels for description, error...
 *
 * @author riccardobusetti
 */
public class ValidatableLayout extends VBox {

    private Label descriptionLabel;
    private ValidatableTextField validatableTextField;
    private Label errorLabel;

    public ValidatableLayout(double spacing, String placeHolder, List<Constraint> constraints, boolean isMandatory, boolean isClearable) {
        super(spacing);

        descriptionLabel = new Label(placeHolder);
        validatableTextField = new ValidatableTextField(constraints, isMandatory, isClearable);
        errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        getChildren().addAll(descriptionLabel, validatableTextField);
    }

    public Integer getValue() {
        return validatableTextField.getIntegerValue();
    }

    public boolean validate() {
        try {
            hideError();
            validatableTextField.validate();
            return true;
        } catch (InvalidInputException exception) {
            showError(exception.getMessage());
            return false;
        }
    }

    public void clear() {
        hideError();
        validatableTextField.setText("");
    }

    private void showError(String text) {
        getChildren().add(errorLabel);
        errorLabel.setText(text);
    }

    private void hideError() {
        getChildren().remove(errorLabel);
    }
}
