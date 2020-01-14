package com.riccardobusetti.calculator.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class containing static methods for mathematical computations.
 * <p>
 * NB: all of these computations are done in the Main UI Thread which is a bad practice,
 * in order to remain within the course topics I haven't made the computations in an async
 * fashion.
 *
 * @author riccardobusetti
 */
public class MathUtil {

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

    public static int primeNumbersNumber(int n) {
        return eratosthenes(n).size();
    }

    public static int eulersTotient(int n) {
        int numberOfCoprimeNumbers = 0;

        for (int i = 1; i <= n; i++) {
            if (gcd(i, n) == 1) numberOfCoprimeNumbers++;
        }

        return numberOfCoprimeNumbers;
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

    public static List<Integer> linearCongruentialGenerator(int a, int b, int m, int n) {
        List<Integer> outputs = new ArrayList<>();
        int x = 0;

        for (int i = 1; i <= n; i++) {
            x = ((a * x) + b) % m;
            outputs.add(x);
        }

        return outputs;
    }

    public static int partition(int n) {
        return (int) ((1 / (4 * n * Math.sqrt(3))) * Math.pow(Math.E, (Math.PI * Math.sqrt((2 * n) / 3.0))));
    }
}
