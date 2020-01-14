package com.riccardobusetti.calculator.history;

import com.riccardobusetti.calculator.domain.Computation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Singleton class responsible of creating a history of all the events that occur in the app.
 *
 * @author riccardobusetti
 */
public class HistoryLogger {

    private static HistoryLogger INSTANCE = null;
    private List<String> events = new ArrayList<>();
    private HistoryObserver<String> observer;

    private HistoryLogger() {
    }

    public static HistoryLogger getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HistoryLogger();
        }

        return INSTANCE;
    }

    public List<String> getEvents() {
        return events;
    }

    public void observe(HistoryObserver<String> observer) {
        this.observer = observer;
    }

    public void dispose() {
        this.observer = null;
    }

    public void logComputation(Computation computation, List<Integer> inputs, List<Integer> outputs) {
        String formattedComputation = formatComputation(computation, inputs, outputs);
        events.add(formattedComputation);

        if (observer != null) {
            observer.observe(formattedComputation);
        }
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

    /**
     * Interface which describes the behavior that an observer must adopt
     * in order to observe the changes happening in the history.
     *
     * @param <T> type of the event.
     */
    public interface HistoryObserver<T> {

        /**
         * Observes the history for any new events that might occur.
         *
         * @param event event that occurs in the history.
         */
        void observe(T event);
    }
}
