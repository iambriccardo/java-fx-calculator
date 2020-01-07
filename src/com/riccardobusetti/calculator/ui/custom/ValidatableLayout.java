package com.riccardobusetti.calculator.ui.custom;

import com.riccardobusetti.calculator.domain.Constraint;
import com.riccardobusetti.calculator.exception.InvalidInputException;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
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

    public ValidatableLayout(double spacing, String placeHolder, List<Constraint> constraints, boolean isClearable) {
        super(spacing);

        descriptionLabel = new Label(placeHolder);

        ImageView clearButton = new ImageView();
        // This icon has been taken from https://material.io/resources/icons/?search=clear&icon=clear&style=round credits to Google.
        clearButton.setImage(new Image("res/clear.png"));
        clearButton.setFitHeight(18);
        clearButton.setPreserveRatio(true);
        clearButton.setFitWidth(18);
        clearButton.setOnMouseClicked(event -> clear());
        clearButton.setVisible(false);
        validatableTextField = new ValidatableTextField(constraints);
        if (isClearable)
            validatableTextField.textProperty().addListener((observable, oldValue, newValue) -> clearButton.setVisible(!newValue.isEmpty()));
        StackPane textFieldContainer = new StackPane(validatableTextField, clearButton);
        StackPane.setAlignment(clearButton, Pos.CENTER_RIGHT);
        StackPane.setMargin(clearButton, new Insets(0, 4, 0, 4));

        errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        getChildren().addAll(descriptionLabel, textFieldContainer);
    }

    public Integer getValue() {
        return validatableTextField.getIntegerValue();
    }

    public void setValue(Integer value) {
        validatableTextField.setText(value.toString());
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

    public void addTextChangedListener(ChangeListener<String> listener) {
        validatableTextField.textProperty().addListener(listener);
    }

    @Override
    public void requestFocus() {
        super.requestFocus();
        validatableTextField.requestFocus();
    }

    public void clear() {
        hideError();
        validatableTextField.clear();
    }

    private void showError(String text) {
        getChildren().add(errorLabel);
        errorLabel.setText(text);
    }

    private void hideError() {
        getChildren().remove(errorLabel);
    }
}
