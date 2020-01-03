package com.riccardobusetti.calculator.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class containing static methods for mathematical computations.
 *
 * @author riccardobusetti
 */
public class MathUtil {

    // TODO: implement all mathematical functions.

    public static List<Integer> eratosthenes(int n) {
        boolean[] a = new boolean[n + 1];
        Arrays.fill(a, true);

        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (a[i]) {
                for (int j = i * i; j <= n; j += i) {
                    a[j] = false;
                }
            }
        }

        List<Integer> outputs = new ArrayList<>();
        for (int i = 2; i < a.length; i++) {
            if (a[i]) outputs.add(i);
        }

        return outputs;
    }


    public static int gcd(int n, int m) {
        while (n != m) {
            if (n > m) {
                n -= m;
            } else {
                m -= n;
            }
        }

        return n;
    }

    public static List<Integer> primeNumbers(int n) {
        List<Integer> outputs = new ArrayList<>();
        outputs.add(1);

        if (n > 1) {
            outputs.addAll(eratosthenes(n));
        }

        return outputs;
    }

    public static List<Integer> coprimeNumbers(int n) {
        List<Integer> outputs = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            if (gcd(i, n) == 1) outputs.add(i);
        }

        return outputs;
    }

    public static List<Integer> primeFactorization(int n) {
        List<Integer> outputs = new ArrayList<>();
        int i = 2;

        while (i <= n) {
            if (n % i == 0) {
                outputs.add(i);
                n /= i;
                i = 2;
            } else {
                i++;
            }
        }

        return outputs;
    }

    public static int sigma(int x, int n) {
        int result = 0;

        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                result += Math.pow(i, x);
            }
        }

        return result;
    }
}
