package com.tnctech.getfit.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestRecordSuite {
    // With Java 15 comes Text blocks!!!  like multi-line strings in Javascript ES6+ or Ruby
    private final String[] testData = {
            "Exercise 1/27/2021    ",
            "- 1.46     ",
            "111       ",
            "119",
            "20 min at 1:45 pm",
            "15:24 min in zone",
            "",
            "  ",
            "" };

    @Test
    void parseDate() throws ParseException {
        String date = "1/27/2021";

        StartExp expected = new StartExp();
        expected.parse(date);
        StartExp actual = new StartExp();
        actual.parse(testData[0]);
        String exp = expected.getValue();
        String act = actual.getValue();

        assertTrue(act.contains(exp));
    }
}
