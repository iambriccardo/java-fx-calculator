package com.riccardobusetti.calculator.ui.custom;

import com.riccardobusetti.calculator.history.HistoryLogger;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;

/**
 * Custom dialog which displays the history of the computations.
 *
 * @author riccardobusetti
 */
public class HistoryDialog extends DialogPane {

    public HistoryDialog() {
        ListView<String> events = new ListView<>();

        // We get all the elements currently in the history.
        for (String event : HistoryLogger.getInstance().getEvents()) {
            events.getItems().add(0, event);
        }

        // We observe any new changes in the history until the dialog is destroyed.
        HistoryLogger.getInstance().observe(event -> events.getItems().add(0, event));

        setContent(events);
    }

    /**
     * Destroys the dialog by disposing the observables in order to avoid memory leaks.
     */
    public void destroy() {
        HistoryLogger.getInstance().dispose();
    }
}
