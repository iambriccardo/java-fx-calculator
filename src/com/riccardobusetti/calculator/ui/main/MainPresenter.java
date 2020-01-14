package com.riccardobusetti.calculator.ui.main;

import com.riccardobusetti.calculator.domain.Computation;
import com.riccardobusetti.calculator.history.HistoryLogger;
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
public class MainPresenter implements MainContract.IMainPresenter {

    private MainContract.IMainView view;
    private Computation currentComputation;

    public MainPresenter(MainContract.IMainView view) {
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
    public Computation getCurrentComputation() {
        return currentComputation;
    }

    @Override
    public void setCurrentComputation(Computation computation) {
        currentComputation = computation;

        view.clearComputationContent();
        view.showInputs(computation);
    }

    @Override
    public void performComputation(List<Integer> inputs) {
        List<Integer> outputs = getGuardedOutputs(inputs);

        if (outputs != null)
            view.showOutputs(outputs);
    }

    @Override
    public void performBatchComputation(int n) {
        List<Integer> inputs = new ArrayList<>();
        List<Integer> outputs = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            inputs.add(i);

            List<Integer> functionOutputs = getGuardedOutputs(Collections.singletonList(i));

            if (functionOutputs != null && functionOutputs.size() > 0)
                outputs.add(functionOutputs.get(0));
        }

        if (inputs.size() == outputs.size())
            view.showGraph(inputs, outputs);
    }

    private List<Integer> getGuardedOutputs(List<Integer> inputs) {
        try {
            return getOutputs(inputs);
        } catch (Exception exception) {
            view.showError("There was an error during " + currentComputation.getLabel() + " computation.\n" + exception.getMessage());
            HistoryLogger.getInstance().logComputation(currentComputation, inputs, null);
            return null;
        }
    }

    private List<Integer> getOutputs(List<Integer> inputs) throws Exception {
        List<Integer> outputs = new ArrayList<>();

        if (currentComputation != null) {
            switch (currentComputation) {
                case ERATOSTHENES_ALGORITHM:
                    outputs.addAll(MathUtil.eratosthenes(inputs.get(0)));
                    break;
                case GCD:
                    outputs.add(MathUtil.gcd(inputs.get(0), inputs.get(1)));
                    break;
                case PRIME_NUMBERS_NUMBER:
                    outputs.add(MathUtil.primeNumbersNumber(inputs.get(0)));
                    break;
                case EULERS_TOTIENT:
                    outputs.add(MathUtil.eulersTotient(inputs.get(0)));
                    break;
                case PRIME_FACTORIZATION:
                    outputs.addAll(MathUtil.primeFactorization(inputs.get(0)));
                    break;
                case SIGMA_FUNCTION:
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
                case PARTITION_FUNCTION:
                    outputs.add(MathUtil.partition(inputs.get(0)));
                    break;
            }
        }

        HistoryLogger.getInstance().logComputation(currentComputation, inputs, outputs);

        return outputs;
    }
}
