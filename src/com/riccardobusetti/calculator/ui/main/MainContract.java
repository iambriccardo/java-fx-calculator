package com.riccardobusetti.calculator.ui.main;

import com.riccardobusetti.calculator.domain.Computation;
import com.riccardobusetti.calculator.ui.Presenter;
import com.riccardobusetti.calculator.ui.View;

import java.util.List;

/**
 * Interface describing the contract between the presenter and the view.
 * <p>
 * This contract is needed because of the MVP architectural pattern adopted in this application.
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
    }

    interface BaseMainPresenter extends Presenter {

        void getAllComputations();

        Computation getCurrentComputation();

        void setCurrentComputation(Computation computation);

        void performComputation(List<Integer> inputs);

        void performBatchComputation(int n);
    }
}
