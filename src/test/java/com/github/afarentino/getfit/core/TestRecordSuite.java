package com.github.afarentino.getfit.core;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestRecordSuite {
    private final String[] testData = {
            "Exercise 1/3/2021 - 11:43 am   ",
            "- .46     ",
            "111       ",
            "119",
            "20 min at 1:45 pm",
            "15:24 in zone",
            "This is line 1 of a note",
            "A note should also support adding additional lines to it  ",
            " " };

    private final String[] testData2 = {
            "1/3/2021",
            "0.75",
            "Additional supported formats testing"
    };

    @Test
    void parseDate() throws ParseException {
        String date = "1/3/2021 11:43 am";
        StartExp expected = new StartExp();
        expected.parse(date);

        StartExp actual = new StartExp();
        actual.parse(testData[0]);

        String exp = expected.getValue();
        String act = actual.getValue();
        assertTrue(act.contains(exp));

        // Make sure we can still parse simpler Date only lines as well.
        date = "1/3/2021";
        expected = new StartExp();
        expected.parse(date);
        actual = new StartExp();
        actual.parse(testData2[0]);

        exp = expected.getValue();
        act = actual.getValue();
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

        distance = ".75";
        expected = new DistanceExp();
        expected.parse(distance);

        actual = new DistanceExp();
        actual.parse(testData2[1]);

        exp = expected.getValue();
        act = actual.getValue();

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
    void parseInZone() throws ParseException {
        String zone = "15:24 in zone";

        InZoneExp expected = new InZoneExp();
        expected.parse(zone);

        InZoneExp actual = new InZoneExp();
        actual.parse(testData[5]);

        String exp = expected.getValue();
        String act = actual.getValue();

        assertTrue(act.startsWith(exp));

    }

    @Test
    void parseTimer() throws ParseException {
        String timer = "20 min at 1:45 pm";

        TimerExp expected = new TimerExp();
        expected.parse(timer);

        TimerExp actual = new TimerExp();
        actual.parse(testData[4]);

        String exp = expected.getValue();
        String act = actual.getValue();

        assertTrue(act.startsWith(exp));

    }

    @Test
    void parseNote() throws ParseException {
        String note = testData[6];
        String noteExtended = testData[7];
        String combined = note + ' ' + noteExtended;
        combined = combined.trim();
        NoteExp actual = new NoteExp();
        actual.parse(note);
        String act = actual.getValue();
        assertTrue(act.startsWith(note));

        actual.parse(noteExtended);
        act = actual.getValue();
        assertTrue(act.startsWith(combined));
    }

    /*
     *  Helps validate the underlying creation
     *  process used to build an ExerciseRecord
     */
    @Test
    void buildRecord() throws ParseException {
        try (Stream<String> lines = Arrays.stream(testData)) {
            List<ExerciseRecord> list = RecordFactory.processLines(lines);
            assertEquals(1, list.size() );
            ExerciseRecord r = (ExerciseRecord) list.get(0);
            System.out.println(r.toString());
        }
    }

}
