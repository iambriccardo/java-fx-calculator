package com.riccardobusetti.calculator.ui.main;

import com.riccardobusetti.calculator.domain.Computation;
import com.riccardobusetti.calculator.ui.Presenter;
import com.riccardobusetti.calculator.ui.View;

import java.util.List;

/**
 * Interface describing the contract between the presenter and the view.
 * <p>
 * This contract is crucial because in our application we are using MVP design pattern.
 *
 * @author riccardobusetti
 */
public interface MainContract {

    interface BaseMainView extends View {

        void showComputationSelection(List<Computation> computations);

        void showInputs(Computation computation);

        void showOutputs(List<Integer> outputs);

        void showGraph(List<Integer> inputs, List<Integer> outputs);

        void clearInputs();

        // TODO: implement graph related methods.
    }

    interface BaseMainPresenter extends Presenter {

        void getAllComputations();

        void setCurrentComputation(Computation computation);

        Computation getCurrentComputation();

        void performComputation(List<Integer> inputs);

        void performBatchComputation(int n);
    }
}
