package com.riccardobusetti.calculator.logging;

import com.riccardobusetti.calculator.domain.Computation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Singleton class responsible of logging all the events that occur in the app.
 *
 * @author riccardobusetti
 */
public class Logger {

    private static Logger INSTANCE = null;

    private List<String> events = new ArrayList<>();

    private Logger() {
    }

    public static Logger getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Logger();
        }

        return INSTANCE;
    }

    public List<String> getEvents() {
        return events;
    }

    public void logComputation(Computation computation, List<Integer> inputs, List<Integer> outputs) {
        events.add(formatComputation(computation, inputs, outputs));
    }

    private String formatComputation(Computation computation, List<Integer> inputs, List<Integer> outputs) {
        return getCurrentDateAsString() + " | " + getComputationLabelCamelCased(computation.getLabel()) +
                inputs.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", ", "(", ")")) +
                " -> " +
                outputs.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", ", "[", "]"));
    }

    private String getComputationLabelCamelCased(String label) {
        String[] words = label.toLowerCase().split(" ");
        StringBuilder output = new StringBuilder();
        output.append(words[0]);

        for (int i = 1; i < words.length; i++) {
            words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1);
            output.append(words[i]);
        }

        return output.toString();
    }

    private String getCurrentDateAsString() {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        return simpleDateFormat.format(currentDate);
    }

    @Override
    public String toString() {
        return String.join("\n", events);
    }
}
