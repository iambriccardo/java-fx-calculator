package com.riccardobusetti.calculator.ui.main;

import com.riccardobusetti.calculator.domain.Computation;
import com.riccardobusetti.calculator.domain.Constraint;
import com.riccardobusetti.calculator.ui.custom.FunctionGraph;
import com.riccardobusetti.calculator.ui.custom.HistoryDialog;
import com.riccardobusetti.calculator.ui.custom.ValidatableLayout;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main class of the application where we render all the layouts in a dynamic fashion. Moreover we
 * react to all the user inputs, clicks and more.
 * <p>
 * Because in this project I didn't use FXML and Controllers, the whole UI code is very long and complex
 * but I tried to use only the course knowledge to build this program.
 *
 * @author riccardobusetti
 */
public class Main extends Application implements MainContract.IMainView {

    private static final int WINDOW_DEFAULT_WIDTH = 648;
    private static final int WINDOW_DEFAULT_HEIGHT = 600;
    private static final String OUTPUTS_DESCRIPTION_LABEL_NO_TEXT = "Insert a value and press compute";
    private static final int BASE_BATCH_RANGE = 1;

    private MainPresenter presenter;
    private List<ValidatableLayout> currentInputs = new ArrayList<>();
    private int batchRange;

    private BorderPane root;
    private GridPane gridContainer;
    private VBox inputsContainer;
    private VBox graphControlsMainContainer;
    private ValidatableLayout graphIntervalValidatableLayout;
    private ComboBox<Computation> computationSelection;
    private FunctionGraph graph;
    private Label outputsResultLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        setUpUi();
        initPresenter();

        Scene scene = new Scene(root, WINDOW_DEFAULT_WIDTH, WINDOW_DEFAULT_HEIGHT);
        scene.getStylesheets().add("res/styles.css");

        primaryStage.setTitle("Java FX function calculator by Riccardo Busetti n.17692");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void setUpUi() {
        root = new BorderPane();

        ImageView upwardArrow = new ImageView();
        // This animation has been taken from https://lottiefiles.com/4033-uploading credits to Akash Nidhi.
        upwardArrow.setImage(new Image("res/arrow.gif"));
        upwardArrow.setFitWidth(200);
        upwardArrow.setFitHeight(200);
        upwardArrow.setPreserveRatio(true);
        upwardArrow.setSmooth(true);
        upwardArrow.setCache(true);
        upwardArrow.setOnMouseEntered(event -> computationSelection.show());

        root.setCenter(upwardArrow);
    }

    private void initPresenter() {
        presenter = new MainPresenter(this);
    }

    @Override
    public void showComputationSelection(List<Computation> computations) {
        if (root != null) {
            computationSelection = new ComboBox<>(FXCollections.observableList(computations));
            computationSelection.setOnMouseEntered(event -> computationSelection.show());
            computationSelection.valueProperty().addListener((obs, oldComputation, newComputation) -> {
                batchRange = BASE_BATCH_RANGE;
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
        gridContainer = new GridPane();
        gridContainer.setPadding(new Insets(16, 16, 16, 16));
        gridContainer.setVgap(16);
        gridContainer.setHgap(16);

        inputsContainer = new VBox();
        inputsContainer.setMinWidth(300);
        inputsContainer.setMaxWidth(300);
        inputsContainer.setSpacing(16);

        computation.getInputs().forEach(input -> {
            ValidatableLayout layout = new ValidatableLayout(
                    4,
                    input.getLabel(),
                    input.getConstraints(),
                    input.isClearable()
            );

            currentInputs.add(layout);
            inputsContainer.getChildren().add(layout);
        });

        Button computeButton = new Button("Compute");
        computeButton.setMinWidth(300);
        computeButton.setMaxWidth(300);
        computeButton.setOnAction(e -> handleComputeButtonClick());

        Label computationDescriptionLabel = new Label(computation.getDescription());
        computationDescriptionLabel.setWrapText(true);
        computationDescriptionLabel.getStyleClass().add("description-label");

        inputsContainer.getChildren().addAll(computeButton, computationDescriptionLabel);

        VBox outputsContainer = new VBox();
        outputsContainer.setSpacing(16);

        Label outputDescriptionLabel = new Label("Function result:");

        outputsResultLabel = new Label(OUTPUTS_DESCRIPTION_LABEL_NO_TEXT);
        outputsResultLabel.setTextFill(Color.BLACK);
        outputsResultLabel.getStyleClass().add("result-label");
        outputsResultLabel.setWrapText(true);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPrefSize(300, 50);
        scrollPane.setContent(outputsResultLabel);

        VBox outputsLabelsContainer = new VBox(outputDescriptionLabel, scrollPane);
        outputsLabelsContainer.setSpacing(4);
        outputsContainer.getChildren().add(outputsLabelsContainer);

        gridContainer.getColumnConstraints().add(new ColumnConstraints(300));
        GridPane.setVgrow(inputsContainer, Priority.ALWAYS);
        GridPane.setVgrow(outputsContainer, Priority.ALWAYS);
        gridContainer.add(inputsContainer, 0, 0);
        gridContainer.add(outputsContainer, 1, 0);

        if (computation.hasGraph())
            performBatchComputation();

        root.setCenter(gridContainer);
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
        // We create the graph only if it is not already present in the grid container.
        if (!gridContainer.getChildren().contains(graph)) {
            // Graph object initialization.
            graph = new FunctionGraph(presenter.getCurrentComputation());
            graph.fitInGrid();
            // Add graph to the right column of the grid container.
            gridContainer.add(graph, 1, 1);
        }

        // Populate the graph.
        graph.populate(inputs, outputs, true);

        // We don't want to recreate the controls each time, thus if they are already inside of our
        // grid container we don't create them twice.
        if (!gridContainer.getChildren().contains(graphControlsMainContainer)) {
            Label controlsDescription = new Label("Decrease or increase the graph interval:");

            Button decreaseButton = new Button("-");
            decreaseButton.setMinWidth(148);
            decreaseButton.setOnAction(event -> handleGraphIntervalButtonClick(false));

            Button increaseButton = new Button("+");
            increaseButton.setMinWidth(148);
            increaseButton.setOnAction(event -> handleGraphIntervalButtonClick(true));

            HBox graphControlsContainer = new HBox(decreaseButton, increaseButton);
            graphControlsContainer.setSpacing(4);

            graphIntervalValidatableLayout = new ValidatableLayout(
                    4,
                    "Plot from 1 to:",
                    Collections.singletonList(Constraint.GREATER_THAN_0),
                    false
            );
            // We populate the field first and then listen for any new changes.
            graphIntervalValidatableLayout.setValue(batchRange);
            // Here we listen for any text change and update the graph accordingly. We call the setValue
            // of this layout whenever we want to change the plot interval of the graph, thus from the
            // two control buttons and the textfield itself.
            graphIntervalValidatableLayout.addTextChangedListener((observable, oldValue, newValue) -> {
                if (graphIntervalValidatableLayout.validate()) {
                    int newBatchRange = graphIntervalValidatableLayout.getValue();

                    // We only perform the computation if the newBatchRange is different from the previous
                    // one, otherwise we are wasting computing power because our functions are all pure
                    // with no edge cases.
                    if (newBatchRange != batchRange) {
                        batchRange = newBatchRange;
                        performBatchComputation();
                    }
                }
            });

            graphControlsMainContainer = new VBox();
            graphControlsMainContainer.setSpacing(4);

            // Add controls vertically.
            graphControlsMainContainer.getChildren().addAll(
                    controlsDescription,
                    graphControlsContainer,
                    graphIntervalValidatableLayout
            );

            // Add controls to the left column of the grid container.
            gridContainer.add(graphControlsMainContainer, 0, 1);
        }
    }

    @Override
    public void showError(String text) {
        showErrorAlert(text);
    }

    @Override
    public void clearComputationContent() {
        if (inputsContainer != null) {
            currentInputs.clear();
            root.getChildren().remove(gridContainer);
        }
    }

    private GridPane getToolbar() {
        GridPane toolbar = new GridPane();
        toolbar.getColumnConstraints().add(new ColumnConstraints(WINDOW_DEFAULT_WIDTH / 3.0));
        toolbar.setPadding(new Insets(16, 16, 16, 16));
        toolbar.getStyleClass().add("menu-bar");
        toolbar.setEffect(getMenuBarShadow());

        Label title = new Label("Function Calculator");
        title.getStyleClass().add("title-label");

        toolbar.add(title, 0, 0);
        toolbar.add(computationSelection, 1, 0);

        return toolbar;
    }

    private HBox getBottomBar() {
        HBox bottomBar = new HBox();
        bottomBar.setPadding(new Insets(16, 16, 16, 16));
        bottomBar.setSpacing(8);
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.getStyleClass().add("menu-bar");
        bottomBar.setEffect(getMenuBarShadow());

        Button clearAllButton = new Button("Clear all");
        clearAllButton.setOnAction(e -> handleClearAllButton());

        Button logButton = new Button("See history");
        logButton.setOnAction(e -> handleLogButtonClick());

        bottomBar.getChildren().addAll(clearAllButton, logButton);

        return bottomBar;
    }

    private void handleComputeButtonClick() {
        List<Integer> inputs = new ArrayList<>();

        for (ValidatableLayout layout : currentInputs) {
            if (layout.validate()) {
                inputs.add(layout.getValue());
            }
        }

        // Only if all the current inputs fields are valid we perform the computation.
        if (inputs.size() == currentInputs.size()) {
            presenter.performComputation(inputs);
        }
    }

    private void handleClearAllButton() {
        outputsResultLabel.setText(OUTPUTS_DESCRIPTION_LABEL_NO_TEXT);

        if (presenter.getCurrentComputation().hasGraph() && graphIntervalValidatableLayout != null) {
            graphIntervalValidatableLayout.setValue(BASE_BATCH_RANGE);
        }

        currentInputs.forEach(ValidatableLayout::clear);
    }

    private void handleLogButtonClick() {
        HistoryDialog dialog = new HistoryDialog();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("History of computations");
        alert.setResizable(true);
        alert.setDialogPane(dialog);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.getDialogPane().setPrefSize(WINDOW_DEFAULT_WIDTH, WINDOW_DEFAULT_HEIGHT);
        alert.show();

        Window window = alert.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(windowsEvent -> {
            dialog.destroy();
            window.hide();
        });
    }

    private void handleGraphIntervalButtonClick(boolean isIncreasing) {
        int newBatchRange = batchRange;

        if (isIncreasing) {
            newBatchRange++;
        } else {
            if (batchRange > BASE_BATCH_RANGE) newBatchRange--;
        }

        if (graphIntervalValidatableLayout != null) {
            graphIntervalValidatableLayout.setValue(newBatchRange);
        }
    }

    private void performBatchComputation() {
        presenter.performBatchComputation(batchRange);
    }

    private void showErrorAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("An error occurred");
        alert.setContentText(text);
        alert.showAndWait();
    }

    private DropShadow getMenuBarShadow() {
        return new DropShadow(10, Color.rgb(0, 0, 0, 0.3));
    }
}
