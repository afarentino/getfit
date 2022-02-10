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

    private String getCSVRowData(ExerciseRecord record) {
        StringBuilder builder = new StringBuilder();
        builder.append(record.start().toString());
        builder.append(",");
        builder.append((record.distance() == null) ? "none" : record.distance().toString());
        builder.append(",");
        builder.append((record.zoneTime() == null) ? "none" : record.zoneTime().toString());
        builder.append(",");
        builder.append((record.totalTime() == null) ? "none" : record.totalTime().toString());
        builder.append(",");
        builder.append((record.avg() == null) ? "none" : record.avg().toString());
        builder.append(",");
        builder.append((record.max() == null) ? "none" : record.max().toString());
        builder.append(",");
        builder.append((record.note() == null) ? "none" : record.note().toString());
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

        List<String> csvLines = new ArrayList<>();
        for (ExerciseRecord row : recordList) {
            String rowData = getCSVRowData(row);
            csvLines.add(rowData);
        }

        // Convert the RecordList into a CSV
        //String csvStr = recordList.stream().map(this::escapeSpecialCharacters)
        //        .collect(Collectors.joining(","));

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
            fw.append("Distance");
            fw.append(",");
            fw.append("ZoneTime");
            fw.append(",");
            fw.append("TotalTime");
            fw.append(",");
            fw.append("AvgHeartRate");
            fw.append(",");
            fw.append("MaxHeartRate");
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
