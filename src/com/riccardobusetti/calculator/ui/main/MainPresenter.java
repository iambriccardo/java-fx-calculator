package com.riccardobusetti.calculator.ui.main;

import com.riccardobusetti.calculator.domain.Computation;

import java.util.Arrays;
import java.util.List;

public class MainPresenter implements MainContract.BaseMainPresenter {

    private MainContract.BaseMainView view;
    private Computation currentComputation;

    public MainPresenter(MainContract.BaseMainView view) {
        this.view = view;
        start();
    }

    @Override
    public void start() {
        getAllComputations();
    }

    @Override
    public void getAllComputations() {
        view.showComputationSelection(Arrays.asList(Computation.values()));
    }

    @Override
    public void setCurrentComputation(Computation computation) {
        currentComputation = computation;

        view.clearInputs();
        view.showInputs(computation);
    }

    @Override
    public Computation getCurrentComputation() {
        return currentComputation;
    }

    @Override
    public void performComputation(List<Integer> inputs) {
        view.showOutputs(Arrays.asList(1, 2, 3));
    }
}
