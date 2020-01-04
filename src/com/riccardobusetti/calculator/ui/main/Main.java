package com.riccardobusetti.calculator.ui.main;

import com.riccardobusetti.calculator.domain.Computation;
import com.riccardobusetti.calculator.ui.custom.ValidatableLayout;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application implements MainContract.BaseMainView {

    private MainPresenter presenter;
    private List<ValidatableLayout> currentInputs = new ArrayList<>();

    private Stage stage;
    private BorderPane root;
    private FlowPane computationContainer;
    private VBox inputsContainer;
    private VBox outputsContainer;
    private ComboBox<Computation> computationSelection;
    private Button clearAllButton;
    private Button computeButton;
    private Button logButton;
    private Button graphButton;
    private Label outputsResultLabel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        setUpUi();

        initPresenter();

        stage = primaryStage;
        stage.setTitle("Java FX function calculator");
        stage.setScene(new Scene(root, 700, 450));
        stage.setResizable(false);
        stage.show();
    }

    private void setUpUi() {
        root = new BorderPane();
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
                    return object != null ? object.getLabel() : "Select a function to compute";
                }

                @Override
                public Computation fromString(String string) {
                    return null;
                }
            });

            root.setTop(getToolbar());
        }
    }

    private HBox getToolbar() {
        HBox toolbar = new HBox();
        toolbar.setPadding(new Insets(16, 16, 16, 16));
        toolbar.setSpacing(8);
        toolbar.setStyle("-fx-background-color: darkgrey");
        toolbar.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Java FX Function Calculator");
        title.setStyle("-fx-font: 16 arial;");

        toolbar.getChildren().addAll(title, computationSelection);

        return toolbar;
    }

    private HBox getBottomBar(boolean showGraph) {
        HBox bottomBar = new HBox();
        bottomBar.setPadding(new Insets(16, 16, 16, 16));
        bottomBar.setSpacing(8);
        bottomBar.setStyle("-fx-background-color: darkgrey");
        bottomBar.setAlignment(Pos.CENTER_LEFT);

        clearAllButton = new Button("Clear all");
        clearAllButton.setOnAction(this::handleClearAllButton);

        logButton = new Button("See log");
        logButton.setOnAction(this::handleLogButtonClick);

        if (showGraph) {
            graphButton = new Button("Show graph");
            graphButton.setOnAction(this::handleGraphButtonClick);
        }

        bottomBar.getChildren().addAll(clearAllButton, logButton);
        if (showGraph) bottomBar.getChildren().add(graphButton);

        return bottomBar;
    }

    private void handleComputeButtonClick(Event event) {
        List<Integer> inputs = new ArrayList<>();

        for (ValidatableLayout layout : currentInputs) {
            if (layout.validate()) {
                inputs.add(layout.getValue());
            }
        }

        if (inputs.size() == currentInputs.size()) {
            presenter.performComputation(inputs);
        }
    }

    private void handleLogButtonClick(Event event) {

    }

    private void handleGraphButtonClick(Event event) {

    }

    @Override
    public void showInputs(Computation computation) {
        computationContainer = new FlowPane();
        computationContainer.setOrientation(Orientation.HORIZONTAL);
        computationContainer.setHgap(16);

        inputsContainer = new VBox();
        inputsContainer.setMinWidth(300);
        inputsContainer.setMaxWidth(300);
        inputsContainer.setPadding(new Insets(16, 16, 16, 16));
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

        computeButton = new Button("Compute");
        computeButton.setMinWidth(268);
        computeButton.setMaxWidth(268);
        computeButton.setOnAction(this::handleComputeButtonClick);
        inputsContainer.getChildren().add(computeButton);

        outputsContainer = new VBox();
        outputsContainer.setPadding(new Insets(16, 16, 16, 16));
        outputsContainer.setSpacing(16);

        Label outputDescriptionLabel = new Label("Function result: ");
        outputsResultLabel = new Label();
        outputsResultLabel.setTextFill(Color.BLACK);
        outputsResultLabel.setStyle("-fx-font: 18 arial;");
        outputsResultLabel.setWrapText(true);
        VBox outputsLabelsContainer = new VBox(outputDescriptionLabel, outputsResultLabel);
        outputsLabelsContainer.setSpacing(4);
        outputsContainer.getChildren().add(outputsLabelsContainer);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color: transparent");
        scrollPane.setPrefSize(300, 0);
        scrollPane.setContent(outputsContainer);

        computationContainer.getChildren().addAll(inputsContainer, scrollPane);

        root.setCenter(computationContainer);
        root.setBottom(getBottomBar(computation.hasGraph()));
    }

    private void handleClearAllButton(Event event) {
        currentInputs.forEach(ValidatableLayout::clear);
    }

    @Override
    public void showOutputs(List<Integer> outputs) {
        outputsResultLabel.setText(outputs.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));
    }

    @Override
    public void clearInputs() {
        if (inputsContainer != null) {
            currentInputs.clear();
            root.getChildren().remove(computationContainer);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
