package com.riccardobusetti.calculator.util;

import com.riccardobusetti.calculator.exception.ExceededIntegerRangeException;

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

    /**
     * Computes all the prime numbers smaller or equal than input using Eratosthenes
     * algorithm.
     *
     * @param n integers used as a boundary of the computation.
     * @return all the prime numbers smaller or equal than n.
     */
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

    /**
     * Computes the greatest common divisor between two integers.
     * <p>
     * This implementation is done with the subtract method which is explained
     * here: https://en.wikipedia.org/wiki/Euclidean_algorithm under implementations.
     *
     * @param n first integer.
     * @param m second integer.
     * @return greatest common divisor.
     */
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

    /**
     * Computes the number of prime numbers smaller or equal than input.
     *
     * @param n integers used as a boundary of the computation.
     * @return number of prime numbers.
     */
    public static int primeNumbersNumber(int n) {
        return eratosthenes(n).size();
    }

    /**
     * Computes the Euler's totient which is the number of coprime numbers with n.
     *
     * @param n integer used for the computation.
     * @return the number of coprime numbers.
     */
    public static int eulersTotient(int n) {
        int numberOfCoprimeNumbers = 0;

        for (int i = 1; i <= n; i++) {
            if (gcd(i, n) == 1) numberOfCoprimeNumbers++;
        }

        return numberOfCoprimeNumbers;
    }

    /**
     * Computes the prime factorization for a given input.
     *
     * @param n inputs to compute the prime factorization of.
     * @return all the prime factors of the input.
     */
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

    /**
     * Computes the sigma function.
     * <p>
     * E.g. sigma(0,14) = 1^0 + 2^0 + 7^0 + 14^0 = 4
     *
     * @param x exponent.
     * @param n base.
     * @return the sigma function result.
     */
    public static int sigma(int x, int n) {
        int result = 0;

        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                result += Math.pow(i, x);
            }
        }

        return result;
    }

    /**
     * Computes the linear congruential generator which is described by this formula:
     * xn+1 = (axn + b) % m with x0 = 0.
     *
     * @param a first integer.
     * @param b second integer.
     * @param m third integer.
     * @param n fourth integer.
     * @return all the randomly generated numbers.
     */
    public static List<Integer> linearCongruentialGenerator(int a, int b, int m, int n) {
        List<Integer> outputs = new ArrayList<>();
        int x = 0;

        for (int i = 1; i <= n; i++) {
            x = ((a * x) + b) % m;
            outputs.add(x);
        }

        return outputs;
    }

    /**
     * Computes the partition function of a given integer.
     *
     * @param n integer to find the partition of.
     * @return the number of partitions which make up the input.
     * @throws Exception thrown when the computation has an error.
     */
    public static int partition(int n) throws Exception {
        int result = (int) ((1 / (4 * n * Math.sqrt(3))) * Math.exp(Math.PI * Math.sqrt((2 * n) / 3.0)));

        if (result >= Integer.MAX_VALUE) throw new ExceededIntegerRangeException();

        return result;
    }
}
