package com.algorithms.metrics;

import java.io.*;
import java.util.List;

public class CSVWriter {
    private final String filename;

    public CSVWriter(String filename) {
        this.filename = filename;
    }

    public void writeHeader(String... headers) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(String.join(",", headers));
        }
    }

    public void appendRow(Object... values) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                if (i > 0) sb.append(",");
                sb.append(values[i]);
            }
            writer.println(sb.toString());
        }
    }
}