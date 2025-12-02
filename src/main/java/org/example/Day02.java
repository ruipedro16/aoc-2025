package org.example;


import com.google.common.collect.Range;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day02 {
    private static final List<Range<Long>> ranges =
            Arrays.stream(
                            Utils.readInput(2).split(",")
                    ).map(Day02::parseRange)
                    .collect(Collectors.toList());

    private static Range<Long> parseRange(@NonNull final String line) {
        var parts = line.split("-");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid range format: " + line);
        }

        long start = Long.parseLong(parts[0]);
        long end = Long.parseLong(parts[1]);

        return Range.closed(start, end);
    }

    private static boolean isRepeatedTwice(@NonNull final String s) {
        int n = s.length();

        if (n % 2 != 0) {
            return false;
        }

        int halfLen = n / 2;
        return s.substring(0, halfLen).equals(s.substring(halfLen));
    }

    private static boolean hasRepeatingPattern(@NonNull final String s) {
        int n = s.length();

        for (int len = 1; len <= n / 2; len++) {
            if (n % len != 0) continue; // must divide evenly

            String candidate = s.substring(0, len);
            boolean matches = true;

            for (int i = len; i < n; i += len) {
                if (!s.substring(i, i + len).equals(candidate)) {
                    matches = false;
                    break;
                }
            }

            if (matches) {
                return true;
            }
        }

        return false;
    }

    private static long sumInvalidIds(@NonNull final Function<Range<Long>, List<Long>> invalidIDsInRange) {
        return ranges.stream()
                .flatMap(range -> invalidIDsInRange.apply(range).stream())
                .mapToLong(Long::longValue)
                .sum();

    }

    public static void printResults() {
        Function<Range<Long>, List<Long>> part1Pred = range ->
                LongStream
                        .rangeClosed(range.lowerEndpoint(), range.upperEndpoint())
                        .boxed()
                        .filter(e -> isRepeatedTwice(e.toString()))
                        .toList();
        long answerPart1 = sumInvalidIds(part1Pred);

        Function<Range<Long>, List<Long>> part2Pred = range ->
                LongStream
                        .rangeClosed(range.lowerEndpoint(), range.upperEndpoint())
                        .boxed()
                        .filter(e -> hasRepeatingPattern(e.toString()))
                        .toList();
        long answerPart2 = sumInvalidIds(part2Pred);

        System.out.println("Day 02 Part 1: " + answerPart1);
        System.out.println("Day 02 Part 2: " + answerPart2);

    }
}
