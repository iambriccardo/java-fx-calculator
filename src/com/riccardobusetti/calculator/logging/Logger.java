package com.riccardobusetti.calculator.logging;

import com.riccardobusetti.calculator.domain.Computation;

import java.util.ArrayList;
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
        return computation.getLabel() +
                inputs.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", ", "(", ")")) +
                " -> " +
                outputs.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", ", "[", "]"));
    }

    @Override
    public String toString() {
        return String.join("\n", events);
    }
}
