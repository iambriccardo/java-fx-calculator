package com.riccardobusetti.calculator.ui.main;

import com.riccardobusetti.calculator.domain.Computation;
import com.riccardobusetti.calculator.util.MathUtil;

import java.util.ArrayList;
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
        List<Integer> outputs = new ArrayList<>();

        if (currentComputation != null) {
            switch (currentComputation) {
                case ERATOSTHENES:
                    outputs.add(MathUtil.eratosthenes(inputs.get(0)));
                    break;
                case GCD:
                    break;
            }
        }

        view.showOutputs(outputs);
    }
}
