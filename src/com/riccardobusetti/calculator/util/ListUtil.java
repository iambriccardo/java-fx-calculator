package com.riccardobusetti.calculator.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Helper class containing methods to manage lists in a simpler
 * and more concise way.
 *
 * @author riccardobusetti
 */
public class ListUtil {

    public static <T> List<T> listOf(T... values) {
        return new ArrayList<>(Arrays.asList(values));
    }
}
