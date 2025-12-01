package org.example;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Utils {
    public static String readInput(int day) {
        if (day <= 0 || day > 12) {
            throw new IllegalArgumentException("Day must be between 1 and 12");
        }

        String filename = String.format("day%02d.txt", day);
        URL resource = Resources.getResource(filename);

        try {
            return Resources.toString(resource, Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static List<String> readInputToLines(int day) {
        if (day <= 0 || day > 12) {
            throw new IllegalArgumentException("Day must be between 1 and 12");
        }

        String filename = String.format("day%02d.txt", day);
        URL resource = Resources.getResource(filename);

        try {
            return Resources.readLines(resource, Charsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
