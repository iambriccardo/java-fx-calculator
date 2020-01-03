package com.riccardobusetti.calculator.ui.main;

import com.riccardobusetti.calculator.domain.Computation;
import com.riccardobusetti.calculator.ui.Presenter;
import com.riccardobusetti.calculator.ui.View;

import java.util.List;

public interface MainContract {

    interface BaseMainView extends View {

        void showComputationSelection(List<Computation> computations);

        void showInputs(Computation computation);

        void showOutputs(List<Integer> outputs);

        void clearInputs();
    }

    interface BaseMainPresenter extends Presenter {

        void getAllComputations();

        void setCurrentComputation(Computation computation);

        Computation getCurrentComputation();

        List<Integer> performComputation(Computation computation);
    }
}
