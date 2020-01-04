package com.riccardobusetti.calculator.ui.custom;

import com.riccardobusetti.calculator.logging.Logger;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;

public class LogDialog extends DialogPane {

    public LogDialog() {
        ListView<String> events = new ListView<>();
        events.getItems().addAll(Logger.getInstance().getEvents());
        setContent(events);
    }
}
