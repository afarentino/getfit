package com.github.afarentino.getfit.core;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class Responsible for constructing new Records as needed
 */
public final class RecordFactory {
    private static final Logger logger = LoggerFactory.getLogger(RecordFactory.class);

    private final List<String> pendingParts;
    private final ExerciseRecord.Builder builder = new ExerciseRecord.Builder();

    /**
     * Attempt to generate a Record
     */
    public static ExerciseRecord createRecord(List<String> parts) {
        if (parts.isEmpty()) {
            return null;
        }
        RecordFactory factory = new RecordFactory(parts);

        logger.info("Generating a new Exercise record...");
        ListIterator<String> partsIterator = parts.listIterator();
        ExerciseRecord.Builder builder = new ExerciseRecord.Builder();
        ExerciseRecord r = factory.create(partsIterator, builder);

        logger.info(r.toString());
        return r;
    }

    /**
     * Process All Lines in the provided input stream converting them into
     * an internal list of Fitness Records
     * @param lines
     */
    public static List<ExerciseRecord> processLines(Stream<String> lines) {
        List<ExerciseRecord> records = new ArrayList<>();
        List<String> parts = new ArrayList<String>();

        // Consume line saving text to convert it into a record
        lines.forEach(s -> {
            logger.debug("Current Line: " + s);   // Just print for now

            // Now it's time to generate a Records
            // Do what we can at the time
            if (s.isBlank()) {
                if (!parts.isEmpty()) {
                    records.add(createRecord(parts));
                    parts.clear();  // Empty the list -- time to process the next Record!
                }
                else {
                    logger.debug("Extra blank line detected ignoring");
                }
            } else {
                parts.add(s);  // Add line to Parts list -- and move on to next in stream
            }
        });

        if (!parts.isEmpty()) { // end of file reached try to generate a record from whatever was read
            ExerciseRecord r = createRecord(parts);
            if (r != null) {
                records.add(r);
                parts.clear();
            }
        }

        return records;
    }

    /**
     * Create a record factory designed to create records for the specified list of Parts
     * @param parts
     */
    public RecordFactory(List<String> parts) {
        this.pendingParts = parts;
    }

    /**
     * Travese Pending Parts List adding components found to a new Excercise Record
     * @return the new Record representing the List of parts associated with this RecordFactory
     */
    public ExerciseRecord create(ListIterator<String> partsIterator, ExerciseRecord.Builder b) {
        // Step 1: Create List of Parts
        while (partsIterator.hasNext()) {
            String text = partsIterator.next();
            int retriesLeft = b.retriesLeft();
            do {
                try {
                    b.buildNext(text);
                    break;  // if we get to this line we parsed the current part successfully.
                } catch (ParseException e) {
                    if (retriesLeft == 0) {
                        logger.info("Failed to parse line: " + text);
                    }
                }
                finally {
                    retriesLeft--;
                }
            } while (retriesLeft > 0);
        }
        // Build what we have and move on...
        return b.build();
    }

}
