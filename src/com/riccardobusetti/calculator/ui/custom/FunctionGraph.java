package com.riccardobusetti.calculator.ui.custom;

import com.riccardobusetti.calculator.domain.Computation;
import javafx.collections.FXCollections;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.List;

/**
 * Custom graph which displays the function plot given a list of values.
 *
 * @author riccardobusetti
 */
public class FunctionGraph extends LineChart<Number, Number> {

    private Computation computation;

    public FunctionGraph(Computation computation) {
        super(new NumberAxis() , new NumberAxis());
        this.computation = computation;
        init();
    }

    private void init() {
        getXAxis().setLabel("Input");
        getYAxis().setLabel("Output");
        setLegendVisible(false);
    }

    public void applyCustomSize() {
        setMaxHeight(250);
        setMaxWidth(300);
    }

    public void populate(List<Integer> inputs, List<Integer> outputs, boolean clearPreviousData) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        setTitle(computation.getLabel() + " [1;" + outputs.size() + "]");

        for (int i = 0; i < inputs.size(); i++) {
            series.getData().add(new XYChart.Data<>(inputs.get(i), outputs.get(i)));
        }

        if (clearPreviousData) {
            // The proper way to update the chart is setting a new empty ObservableList, the only,
            // downside is that it won't have the fancy animations.
            setData(FXCollections.observableArrayList());
        }

        getData().add(series);
    }
}
