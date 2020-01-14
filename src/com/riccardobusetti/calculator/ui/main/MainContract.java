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

    /**
     * Interface describing the behavior of the {@link Main}.
     *
     * @author riccardobusetti
     */
    interface IMainView extends View {

        /**
         * Shows a selection interface to select a specific {@link Computation} to perform.
         *
         * @param computations list of all the computations available to choose from.
         */
        void showComputationSelection(List<Computation> computations);

        /**
         * Shows the inputs to the user, thus letting him input all the necessary values.
         *
         * @param computation current selected computation which contains which inputs to show.
         */
        void showInputs(Computation computation);

        /**
         * Shows the outputs to the user.
         *
         * @param outputs list of all the function outputs.
         */
        void showOutputs(List<Integer> outputs);

        /**
         * Shows the graph to the user.
         * <p>
         * NB: both lists have a one to one correspondence.
         *
         * @param inputs  list of the function inputs.
         * @param outputs list of the function outputs.
         */
        void showGraph(List<Integer> inputs, List<Integer> outputs);

        /**
         * Shows an error to the user.
         *
         * @param text text that must be displayed to the user.
         */
        void showError(String text);

        /**
         * Clears all the content of the current computation.
         */
        void clearComputationContent();
    }

    /**
     * Interface describing the behavior of the {@link MainPresenter}.
     *
     * @author riccardobusetti
     */
    interface IMainPresenter extends Presenter {

        /**
         * Gets all the computations.
         */
        void getAllComputations();

        /**
         * Gets the current selected computation in order for the UI
         * to know which computation has been chosen by the user.
         *
         * @return the current computation.
         */
        Computation getCurrentComputation();

        /**
         * Sets the current computation.
         *
         * @param computation computation that needs to be marked as current.
         */
        void setCurrentComputation(Computation computation);

        /**
         * Performs the current computation with given input.
         *
         * @param inputs list of the computation inputs.
         */
        void performComputation(List<Integer> inputs);

        /**
         * Performs the current computation in bath from 1 to n.
         *
         * @param n maximum value of the interval.
         */
        void performBatchComputation(int n);
    }
}
