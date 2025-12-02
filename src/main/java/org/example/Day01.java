package org.example;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day01 {
    private static final List<Integer> input = Utils.readInputToLines(1)
            .stream()
            .map(Day01::parseLine)
            .collect(Collectors.toList());

    private static final int initial_position = 50;
    private static final List<Integer> positions = getPositions(input);

    private static int parseLine(@NonNull final String line) {
        char direction = line.charAt(0);
        int value = Integer.parseInt(line.substring(1));
        return direction == 'R' ? value : -value;
    }

    private static List<Integer> getPositions(@NonNull final List<Integer> input) {
        List<Integer> positions = new ArrayList<>();
        positions.add(initial_position);

        for (int movement : input) {
            var new_position = (positions.getLast() + movement) % 100;
            positions.add(new_position);
        }

        return positions;
    }

    public static void part1() {
        long answer = positions.stream().filter(p -> p == 0).count();
        System.out.println("Day 01 Part 1: " + answer);
    }

    private static int zeroCrossingsFromMovement(int position, int movement) {
        return movement > 0
                ? Math.floorDiv(position + movement, 100) - Math.floorDiv(position, 100)
                : Math.floorDiv(position - 1, 100) - Math.floorDiv(position + movement - 1, 100);
    }

    public static void part2() {
        int zero_crossings = 0;

        for (int i = 0; i < input.size(); i++) {
            int movement = input.get(i);
            int current_position = positions.get(i);
            zero_crossings += zeroCrossingsFromMovement(current_position, movement);
        }

        System.out.println("Day 01 Part 2: " + zero_crossings);
    }
}