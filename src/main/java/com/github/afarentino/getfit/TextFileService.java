package com.github.afarentino.getfit;

import com.github.afarentino.getfit.core.RecordFactory;
import com.github.afarentino.getfit.core.ExerciseRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

/**
 * Using Java NIO and Java 8 Streams, lazily read a text file and parse lines into
 * Fitness Records
 *
 * Constraints -> File read will not have more than 1 year of records (365)
 */
public class TextFileService {
    private static final Logger logger = LoggerFactory.getLogger(TextFileService.class);

    private String fileName;
    private List<ExerciseRecord> recordList;

    public TextFileService(String fileName) {
        this.fileName = fileName;
    }

    // Escape special characters problematic for CSV files
    private String esc(String data) {
        String escapedData = data;
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    private String getCSVRowData(ExerciseRecord record) {
        StringBuilder builder = new StringBuilder();
        builder.append(esc(record.start().toString()));
        builder.append(",");
        builder.append((record.distance() == null) ? "0.0" : esc(record.distance().toString()));
        builder.append(",");
        builder.append((record.zoneTime() == null) ? "0.0" : esc(record.zoneTime().toString()));
        builder.append(",");
        builder.append((record.totalTime() == null) ? "" : esc(record.totalTime().toString()));
        builder.append(",");
        builder.append((record.calories() == null) ? "" : esc(record.calories().toString()));
        builder.append(",");
        builder.append((record.avg() == null) ? "" : esc(record.avg().toString()));
        builder.append(",");
        builder.append((record.max() == null) ? "" : esc(record.max().toString()));
        builder.append(",");
        builder.append((record.note() == null) ? "" : esc(record.note().toString()));
        String data = builder.toString();
        return data;
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
        // TODO: Refactor adding this into it's own methods...
        List<String> csvLines = new ArrayList<>();
        for (ExerciseRecord row : recordList) {
            String rowData = getCSVRowData(row);
            csvLines.add(rowData);
        }

        String csvDir = path.toFile().getParent();
        String fileNameWithoutExt = this.fileName.replaceFirst("[.][^.]+$", "");
        String csvFileName = fileNameWithoutExt + ".csv";

        File csvFile = new File(csvDir, csvFileName);
        if (csvFile.exists()) {
            csvFile.delete();  // Delete old file...
        }
        try (FileWriter fw = new FileWriter(csvFile)) {
            fw.append("Start");
            fw.append(",");
            fw.append("Distance (miles)");
            fw.append(",");
            fw.append("Zone Time (minutes)");
            fw.append(",");
            fw.append("Elapsed Time (nearest minute)");
            fw.append(",");
            fw.append("Calories Burned");
            fw.append(",");
            fw.append("Avg Heart Rate");
            fw.append(",");
            fw.append("Max Heart Rate");
            fw.append(",");
            fw.append("Notes");
            fw.append("\n");   // End Header Record
            for (String csvLine : csvLines) {
                fw.append(csvLine);
                fw.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return csvFile;
    }
}
