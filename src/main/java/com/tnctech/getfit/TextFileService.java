package com.tnctech.getfit;

import com.tnctech.getfit.core.RecordFactory;
import com.tnctech.getfit.core.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.stream.Stream;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;

/**
 * Using Java NIO and Java 8 Streams, lazily read a text file and parse lines into
 * Fitness Records
 *
 * Constraints -> File read will not have more than 1 year of records (365)
 */
public class TextFileService {
    private String fileName;
    private List<Record> recordList;

    public TextFileService(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Process All Lines in the input stream converting them into
     * an internal list of Fitness Records
     * @param lines
     */
    public static List<Record> processLines(Stream<String> lines) {
        List<Record> records = new ArrayList<Record>();
        List<String> parts = new ArrayList<String>();

        // Consume line saving text to convert it into a record
        lines.forEach(s -> {
            System.out.println("Read Line: " + s);   // Just print for now
            if (s.isBlank()) {
                if (!parts.isEmpty()) {
                    RecordFactory parser = new RecordFactory(parts);
                    System.out.println("Generate a record of parts and add it to the recordList");
                    parts.clear();  // Empty the list -- time to process the next Record!
                }
                else {
                    System.out.println("Extra blank line detected ignoring");
                }
            } else {
                parts.add(s);  // Add line to Parts list -- and move on to next one in File
            }
        });

        return records;
    }

    /**
     * Convert list of Records representing the current file
     */
    public File convertToCSV() {
        if (this.fileName == null) {
            throw new IllegalStateException("fileName cannot be null");
        }

        Path path = Path.of(this.fileName);
        try (Stream<String> lines = Files.lines(path)) {
            this.recordList = processLines(lines);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // Convert the RecordList into a CSV
        return null;
    }
}
