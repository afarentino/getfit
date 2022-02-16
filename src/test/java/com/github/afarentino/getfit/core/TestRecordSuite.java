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
            "Additional supported formats testing",
            "15 min at 2:15 pm"
    };

    private final String[] testData3 = {
            "3/15/2021",
            "1.69",
            "175",                 // Assert this is Calories Burned
            "16:21 min job zk w",  // Assert this should be a Zone Time
            "111",
            "123",
            "20 min at 12:05 pm",  // This line contains the elapsed/total time
            " "
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

    /*
     * Start records support re-parsing at runtime
     * Validate this
     */
    @Test
    void parseDateAddTime() throws ParseException {
        String date = "1/3/2021";
        String time = "11:43 am";

        StartExp expected = new StartExp();
        expected.parse(date);
        StartExp actual = new StartExp();
        actual.parse(testData2[0]);

        String exp = expected.getValue();
        String act = actual.getValue();
        assertTrue(act.contains(exp));

        // Add time and validate
        String dateTime = "1/3/2021 11:43 am";
        expected = new StartExp();
        expected.parse(date);
        exp = expected.getValue();
        actual.setValue(time);
        assertTrue(act.startsWith(exp));
    }

    @Test
    void parseDistance() throws ParseException {
        String distance = ".46";

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
     *  Helps ensure the underlying creation
     *  process used to build an ExerciseRecord has not regressed
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

    @Test
    void validateRecord() throws ParseException {
        ExerciseRecord r;
        try (Stream<String> lines = Arrays.stream(testData3)) {
            List<ExerciseRecord> list = RecordFactory.processLines(lines);
            assertEquals(1, list.size() );
            r = (ExerciseRecord) list.get(0);
            System.out.println(r.toString());
        }

        assertEquals("175", r.calories());
        assertEquals( "16", r.totalTime() );  // Elapsed should round up + display as an int not decimal
    }

}
