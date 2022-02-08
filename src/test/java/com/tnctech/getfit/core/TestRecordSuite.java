package com.tnctech.getfit.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestRecordSuite {
    private final String[] testData = {
            "Exercise 1/27/2021    ",
            "- 1.46     ",
            "111       ",
            "119",
            "20 min at 1:45 pm",
            "15:24 min in zone",
            "This is line 1 of a note",
            "A note should also support adding additional lines to it  ",
            " " };

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

    @Test
    void parseDistance() throws ParseException {
        String distance = "1.46";

        DistanceExp expected = new DistanceExp();
        expected.parse(distance);

        DistanceExp actual = new DistanceExp();
        actual.parse(testData[1]);

        String exp = expected.getValue();
        String act = actual.getValue();

        assertTrue(act.startsWith(exp));
    }

    @Test
    void parseAvgHeartRate() throws ParseException {
        String rate = "111";

        HeartRateExp expected = new HeartRateExp();
        expected.parse(rate);

        HeartRateExp actual = new HeartRateExp();
        actual.parse(testData[2]);

        String exp = expected.getValue();
        String act = actual.getValue();

        assertTrue(act.startsWith(exp));
    }

    @Test
    void parseTime() throws ParseException {
        String time = "15:24 min ";

        TimeExp expected = new TimeExp();
        expected.parse(time);

        TimeExp actual = new TimeExp();
        actual.parse(testData[5]);

        String exp = expected.getValue();
        String act = actual.getValue();

        assertTrue(act.startsWith(exp));

    }

    @Test
    void parseNote() throws ParseException {
        String note = testData[6];
        String noteExtended = testData[7];

        NoteExp actual = new NoteExp();
        actual.parse(note);
        String act = actual.getValue();
        assertTrue(act.startsWith(note));

        actual.parse(noteExtended);
        act = actual.getValue();
        assertTrue(act.startsWith(note + noteExtended));
    }

    /*
     *  Helps validate the underlying creation
     *  process used to build an ExerciseRecord
     */
    @Test
    void buildRecord() throws ParseException {
        try (Stream<String> lines = Arrays.stream(testData)) {

            assertEquals(1, 2);
        }
    }

}
