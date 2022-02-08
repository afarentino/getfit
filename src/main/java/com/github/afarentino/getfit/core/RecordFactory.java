package com.github.afarentino.getfit.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Class Responsible for constructing new Records as needed
 */
public final class RecordFactory {

    private final List<String> pendingParts;
    private static final int PART_TYPES = 7;

    /**
     * Process All Lines in the provided input stream converting them into
     * an internal list of Fitness Records
     * @param lines
     */
    public static List<Record> processLines(Stream<String> lines) {
        List<Record> records = new ArrayList<Record>();
        List<String> parts = new ArrayList<String>();

        // Consume line saving text to convert it into a record
        lines.forEach(s -> {
            System.out.println("Current Line: " + s);   // Just print for now

            // Now it's time to generate a Records
            // Do what we can at the time
            if (s.isBlank()) {
                if (!parts.isEmpty()) {
                    RecordFactory factory = new RecordFactory(parts);
                    System.out.println("Generating a new Exercise record...");
                    ExerciseRecord r = factory.create();
                    records.add(r);
                    parts.clear();  // Empty the list -- time to process the next Record!
                }
                else {
                    System.out.println("Extra blank line detected ignoring");
                }
            } else {
                parts.add(s);  // Add line to Parts list -- and move on to next in stream
            }
        });

        return records;
    }
    /**
     * Create a record factory designed to create records for the specified list of Parts
     * @param parts
     */
    public RecordFactory(List<String> parts) {
        this.pendingParts = parts;
    }

    public Component createStartExp(String text) throws ParseException {
        StartExp exp = new StartExp();
        exp.parse(text);
        return exp;
    }

    public Component createDistanceExp(String text) throws ParseException {
        DistanceExp exp = new DistanceExp();
        exp.parse(text);
        return exp;
    }

    public Component createInZoneExp(String text) throws ParseException {
        InZoneExp exp = new InZoneExp();
        exp.parse(text);
        return exp;
    }

    public Component createTimeExp(String text) throws ParseException {
        TimeExp exp = new TimeExp();
        exp.parse(text);
        return exp;
    }

    public Component createHeartRateExp(String text) throws ParseException {
        HeartRateExp exp = new HeartRateExp();
        exp.parse(text);
        return exp;
    }

    public Component createNoteExp(String text) throws ParseException {
        NoteExp exp = new NoteExp();
        exp.parse(text);
        return exp;
    }

    /**
     * Travese Pending Parts List adding components found to a Record
     * @return the new Record representing the List of parts associated with this RecordFactory
     */
    public ExerciseRecord create() {
        // Step 1: Create List of Parts
        for ( int i=0; i < pendingParts.size(); i++ ) {
            //builder.add(partFor(pendingParts.get(0)) );
        }

        return null;
    }

    private Component partFor(String text) throws IllegalStateException {
        int i = 0;
        Component c = null;
        ExerciseRecord.Builder b = new ExerciseRecord.Builder();
        /** try ( i < PART_TYPES && c ) {

        } */
        return null;
    }

}
