package org.example;

import lombok.NonNull;

import java.util.List;

public class Day03 {
    private static final List<List<Integer>> input = Utils.readInputToLines(3)
            .stream()
            .map(Day03::parseLine)
            .toList();

    private static List<Integer> parseLine(@NonNull final String line) {
        if (!line.matches("\\d+")) {
            throw new IllegalArgumentException("String contains non-digit characters: " + line);
        }

        return line
                .chars()
                .mapToObj(c -> Integer.parseInt(Character.toString((char) c)))
                .toList();
    }

    private static long chooseBatteries(@NonNull final List<Integer> bank, int n) {
        if (n <= 0 || n > bank.size()) {
            throw new IllegalArgumentException("Invalid value for n: " + n);
        }

        StringBuilder result = new StringBuilder();
        int start = 0;

        for (int i = 0; i < n; i++) {
            // We need to pick (n - i) digits in total, including this one
            // We must leave at least (n - i - 1) digits after the one we pick
            // So we can search up to position: bank.size() - (n - i)
            int searchEnd = bank.size() - n + i;

            // Find the maximum digit in the valid range [start, searchEnd]
            int maxDigit = -1;
            int maxPos = -1;
            for (int j = start; j <= searchEnd; j++) {
                if (bank.get(j) > maxDigit) {
                    maxDigit = bank.get(j);
                    maxPos = j;
                }
            }

            result.append(maxDigit);
            start = maxPos + 1;
        }

        return Long.parseLong(result.toString());
    }

    private static long answer(int n) {
        return input
                .stream()
                .mapToLong(bank -> chooseBatteries(bank, n))
                .sum();
    }

    public static void printResults() {
        System.out.println("Day 03 Part 1: " + answer(2));
        System.out.println("Day 03 Part 2: " + answer(12));
    }
}
