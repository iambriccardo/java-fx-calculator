package com.riccardobusetti.calculator.ui.custom;

import com.riccardobusetti.calculator.logging.Logger;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;

public class LogDialog extends DialogPane {

    public LogDialog() {
        ListView<String> events = new ListView<>();
        for (String event : Logger.getInstance().getEvents()) {
            events.getItems().add(0, event);
        }
        setContent(events);
    }
}
