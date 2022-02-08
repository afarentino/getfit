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
     * Convert list of Records representing the current file
     */
    public File convertToCSV() {
        if (this.fileName == null) {
            throw new IllegalStateException("fileName cannot be null");
        }
        Path path = Path.of(this.fileName);
        try (Stream<String> lines = Files.lines(path)) {
            this.recordList = RecordFactory.processLines(lines);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // Convert the RecordList into a CSV
        return null;
    }
}
