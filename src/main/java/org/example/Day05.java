package org.example;

import com.google.common.collect.Range;
import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day05 {
    private static final List<Range<Long>> ranges = Utils.readInputToLines(5)
            .stream()
            .map(Day05::parseRange)
            .filter(Objects::nonNull)
            .toList();

    private static final List<Long> ingredientIDs = Utils.readInputToLines(5)
            .stream()
            .map(x -> {
                try {
                    return Long.parseLong(x);
                } catch (Exception ignored) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .toList();

    private static Range<Long> parseRange(@NonNull final String line) {
        try {
            var parts = line.split("-");

            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid range format: " + line);
            }

            long start = Long.parseLong(parts[0]);
            long end = Long.parseLong(parts[1]);

            return Range.closed(start, end);
        } catch (Exception ignored) {
            return null;
        }
    }

    private static boolean isInAnyRange(Long l,  @NonNull final List<Range<Long>> ranges) {
        return ranges
                .stream()
                .anyMatch(range -> range.lowerEndpoint() <= l && range.upperEndpoint() >= l);
    }

    private static List<Range<Long>> mergeRanges(@NonNull final List<Range<Long>> ranges) {
        if (ranges.isEmpty()) {
            return new ArrayList<>();
        }

        List<Range<Long>> sorted = new ArrayList<>(ranges);
        sorted.sort(Comparator.comparing(Range::lowerEndpoint));

        List<Range<Long>> merged = new ArrayList<>();
        Range<Long> current = sorted.getFirst();

        for (int i = 1; i < sorted.size(); i++) {
            Range<Long> next = sorted.get(i);

            // Check if ranges overlap or are adjacent
            if (current.upperEndpoint() >= next.lowerEndpoint() - 1) {
                // Ranges overlap so we merge ranges
                current = Range.closed(
                    current.lowerEndpoint(),
                    Math.max(current.upperEndpoint(), next.upperEndpoint())
                );
            } else {
                // No overlap
                merged.add(current);
                current = next;
            }
        }

        merged.add(current);

        return merged;
    }

    private static long countUniqueInRanges(@NonNull final List<Range<Long>> ranges) {
        List<Range<Long>> merged = mergeRanges(ranges);

        return merged.stream()
                .mapToLong(range -> range.upperEndpoint() - range.lowerEndpoint() + 1)
                .sum();
    }

    public static void printResults() {
        long answerPart1 = ingredientIDs
                .stream()
                .filter(id -> isInAnyRange(id, ranges))
                .count();

        long answerPart2 = countUniqueInRanges(ranges);

        System.out.println("Day 05 Part 1: " + answerPart1);
        System.out.println("Day 05 Part 2: " + answerPart2);
    }
}
