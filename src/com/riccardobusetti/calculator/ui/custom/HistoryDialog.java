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

        for (String event: HistoryLogger.getInstance().getEvents()) {
            events.getItems().add(0, event);
        }

        HistoryLogger.getInstance().observe(event -> events.getItems().add(0, event));

        setContent(events);
    }
}
