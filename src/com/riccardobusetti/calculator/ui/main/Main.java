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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

    private static final String OUTPUTS_DESCRIPTION_LABEL_NO_TEXT = "Insert a value and press compute";
    private static final int BASE_BATCH_RANGE = 1;

    private MainPresenter presenter;
    private List<ValidatableLayout> currentInputs = new ArrayList<>();
    private int batchRange = BASE_BATCH_RANGE;

    private Stage stage;
    private BorderPane root;
    private FlowPane computationContainer;
    private VBox inputsContainer;
    private VBox outputsContainer;
    private VBox graphContainer;
    private ComboBox<Computation> computationSelection;
    private Button clearAllButton;
    private Button computeButton;
    private Button logButton;
    private LineChart<Number, Number> graph;
    private Label outputsResultLabel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        setUpUi();

        initPresenter();

        stage = primaryStage;
        stage.setTitle("Java FX function calculator");
        stage.setScene(new Scene(root, 700, 525));
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
            computationSelection = new ComboBox<>(FXCollections.observableList(computations));
            computationSelection.valueProperty().addListener((obs, oldComputation, newComputation) -> {
                presenter.setCurrentComputation(newComputation);
            });
            computationSelection.setConverter(new StringConverter<>() {
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

        Label outputDescriptionLabel = new Label("Function result:");
        outputsResultLabel = new Label(OUTPUTS_DESCRIPTION_LABEL_NO_TEXT);
        outputsResultLabel.setTextFill(Color.BLACK);
        outputsResultLabel.setStyle("-fx-font: 18 arial;");
        outputsResultLabel.setWrapText(true);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: transparent");
        scrollPane.setPrefSize(300, 50);
        scrollPane.setContent(outputsResultLabel);
        VBox outputsLabelsContainer = new VBox(outputDescriptionLabel, scrollPane);
        outputsLabelsContainer.setSpacing(4);
        outputsContainer.getChildren().add(outputsLabelsContainer);

        computationContainer.getChildren().addAll(inputsContainer, outputsContainer);

        if (computation.hasGraph())
            performBatchComputation();

        root.setCenter(computationContainer);
        root.setBottom(getBottomBar());
    }

    @Override
    public void showOutputs(List<Integer> outputs) {
        outputsResultLabel.setText(outputs.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));
    }

    @Override
    public void showGraph(List<Integer> inputs, List<Integer> outputs) {
        outputsContainer.getChildren().remove(graphContainer);

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Input");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Output");

        graphContainer = new VBox();
        graphContainer.setSpacing(4);

        Label graphDescriptionLabel = new Label("Function graph from 1 to " + outputs.size() + ":");

        graph = new LineChart<>(xAxis, yAxis);
        graph.setMaxHeight(200);
        graph.setMaxWidth(300);
        graph.setTitle(presenter.getCurrentComputation().getLabel());
        XYChart.Series series = new XYChart.Series();
        series.setName("Computation of the function from 1 to " + outputs.size());
        for (int i = 0; i < inputs.size(); i++) {
            series.getData().add(new XYChart.Data<>(inputs.get(i), outputs.get(i)));
        }
        graph.getData().add(series);

        Label controlsDescription = new Label("Change the graph interval");

        Button decraeseButton = new Button("-");
        decraeseButton.setOnAction(event -> handleGraphIntervalButtonClick(false));

        Button increaseButton = new Button("+");
        increaseButton.setOnAction(event -> handleGraphIntervalButtonClick(true));

        HBox graphControlsContainer = new HBox(controlsDescription, decraeseButton, increaseButton);
        graphControlsContainer.setAlignment(Pos.CENTER_LEFT);
        graphControlsContainer.setSpacing(4);

        graphContainer.getChildren().addAll(graphDescriptionLabel, graph, graphControlsContainer);

        outputsContainer.getChildren().add(graphContainer);
    }

    @Override
    public void clearInputs() {
        if (inputsContainer != null) {
            currentInputs.clear();
            root.getChildren().remove(computationContainer);
        }
    }

    private HBox getToolbar() {
        HBox toolbar = new HBox();
        toolbar.setPadding(new Insets(16, 16, 16, 16));
        toolbar.setSpacing(8);
        toolbar.setStyle("-fx-background-color: #ff7043");
        toolbar.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Java FX Function Calculator");
        title.setStyle("-fx-font: 16 arial;");

        toolbar.getChildren().addAll(title, computationSelection);

        return toolbar;
    }

    private HBox getBottomBar() {
        HBox bottomBar = new HBox();
        bottomBar.setPadding(new Insets(16, 16, 16, 16));
        bottomBar.setSpacing(8);
        bottomBar.setStyle("-fx-background-color: #ff7043");
        bottomBar.setAlignment(Pos.CENTER_LEFT);

        clearAllButton = new Button("Clear all");
        clearAllButton.setOnAction(this::handleClearAllButton);

        logButton = new Button("See log");
        logButton.setOnAction(this::handleLogButtonClick);

        bottomBar.getChildren().addAll(clearAllButton, logButton);

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

    private void handleClearAllButton(Event event) {
        outputsResultLabel.setText(OUTPUTS_DESCRIPTION_LABEL_NO_TEXT);
        if (presenter.getCurrentComputation().hasGraph()) {
            batchRange = BASE_BATCH_RANGE;
            performBatchComputation();
        }
        currentInputs.forEach(ValidatableLayout::clear);
    }

    private void handleLogButtonClick(Event event) {

    }

    private void handleGraphIntervalButtonClick(boolean isIncreasing) {
        if (isIncreasing) {
            batchRange++;
        } else {
            if (batchRange > 1) {
                batchRange--;
            }
        }

        performBatchComputation();
    }

    private void performBatchComputation() {
        presenter.performBatchComputation(batchRange);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
