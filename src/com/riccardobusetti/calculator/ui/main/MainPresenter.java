package com.riccardobusetti.calculator.ui.main;

import com.riccardobusetti.calculator.domain.Computation;
import com.riccardobusetti.calculator.util.MathUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class that acts as the presenter for the main view by managing all the business
 * logic and dispatching UI events.
 *
 * @author riccardobusetti
 */
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
        view.showOutputs(getOutputs(inputs));
    }

    @Override
    public void performBatchComputation(int n) {
        List<Integer> inputs = new ArrayList<>();
        List<Integer> outputs = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            inputs.add(i);
            outputs.add(getOutputs(Collections.singletonList(i)).get(0));
        }

        view.showGraph(inputs, outputs);
    }

    private List<Integer> getOutputs(List<Integer> inputs) {
        List<Integer> outputs = new ArrayList<>();

        if (currentComputation != null) {
            switch (currentComputation) {
                case ERATOSTHENES:
                    outputs.addAll(MathUtil.eratosthenes(inputs.get(0)));
                    break;
                case GCD:
                    outputs.add(MathUtil.gcd(inputs.get(0), inputs.get(1)));
                    break;
                case PRIME_NUMBERS:
                    outputs.add(MathUtil.primeNumbers(inputs.get(0)));
                    break;
                case COPRIME_NUMBERS:
                    outputs.add(MathUtil.coprimeNumbers(inputs.get(0)));
                    break;
                case PRIME_FACTORIZATION:
                    outputs.addAll(MathUtil.primeFactorization(inputs.get(0)));
                    break;
                case SIGMA:
                    outputs.add(MathUtil.sigma(inputs.get(0), inputs.get(1)));
                    break;
                case LINEAR_CONGRUENTIAL_GENERATOR:
                    outputs.addAll(MathUtil.linearCongruentialGenerator(
                            inputs.get(0),
                            inputs.get(1),
                            inputs.get(2),
                            inputs.get(3))
                    );
                    break;
                case PARTITION:
                    outputs.add(MathUtil.partition(inputs.get(0)));
                    break;
            }
        }

        return outputs;
    }
}
