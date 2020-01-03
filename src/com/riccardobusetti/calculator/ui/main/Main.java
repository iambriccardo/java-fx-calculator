package com.riccardobusetti.calculator.ui.main;

import com.riccardobusetti.calculator.domain.Computation;
import com.riccardobusetti.calculator.ui.custom.ValidatableLayout;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application implements MainContract.BaseMainView {

    private MainPresenter presenter;
    private List<ValidatableLayout> currentInputs = new ArrayList<>();

    private FlowPane root;
    private VBox inputsContainer;
    private ComboBox<Computation> computationSelection;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new FlowPane();
        root.setAlignment(Pos.TOP_LEFT);
        root.setOrientation(Orientation.VERTICAL);
        root.setVgap(16);
        root.setHgap(16);

        initPresenter();

        primaryStage.setTitle("Java FX function calculator");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }

    private void initPresenter() {
        presenter = new MainPresenter(this);
    }

    @Override
    public void showComputationSelection(List<Computation> computations) {
        if (root != null) {
            computationSelection = new ComboBox<Computation>(FXCollections.observableList(computations));
            computationSelection.valueProperty().addListener((obs, oldComputation, newComputation) -> {
                presenter.setCurrentComputation(newComputation);
            });
            computationSelection.setConverter(new StringConverter<Computation>() {
                @Override
                public String toString(Computation object) {
                    return object != null ? object.name() : "Select a function to execute";
                }

                @Override
                public Computation fromString(String string) {
                    return null;
                }
            });

            root.getChildren().add(computationSelection);
        }
    }

    @Override
    public void showInputs(Computation computation) {
        inputsContainer = new VBox();
        inputsContainer.setSpacing(16);

        computation.getInputs().forEach(input -> {
            ValidatableLayout layout = new ValidatableLayout(
                    4,
                    input.getLabel(),
                    input.getConstraints(),
                    input.isMandatory(),
                    input.isClearable()
            );

            currentInputs.add(layout);
            inputsContainer.getChildren().add(layout);
        });

        Button computeButton = new Button("Compute");
        computeButton.setOnAction(this::handleComputeButtonClick);
        inputsContainer.getChildren().add(computeButton);

        root.getChildren().add(inputsContainer);
    }

    private void handleComputeButtonClick(Event event) {
        List<Integer> inputs = new ArrayList<>();

        for (ValidatableLayout layout: currentInputs) {
            if (layout.validate()) {
                inputs.add(layout.getValue());
            }
        }

        if (inputs.size() == currentInputs.size()) {
            presenter.performComputation(inputs);
        }
    }

    @Override
    public void showOutputs(List<Integer> outputs) {

    }

    @Override
    public void clearInputs() {
        if (inputsContainer != null) {
            currentInputs.clear();
            root.getChildren().remove(inputsContainer);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
