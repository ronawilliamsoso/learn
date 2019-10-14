package com.example.demo.utils;


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class WriteCsv {
	private static final String SAMPLE_CSV_FILE = "/Users/wangwei/Documents/workspace/learning/demo/src/main/java/com/example/demo/file/wcar.csv";

	public static void main(String[] args) throws IOException {
		try (
				BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE));

				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
						.withHeader("ID", "Name", "Designation", "Company"))
		) {
			csvPrinter.printRecord("1", "Sundar Pichai ♥", "CEO", "Google");
			csvPrinter.printRecord("2", "Satya Nadella", "CEO", "Microsoft");
			csvPrinter.printRecord("3", "Tim cook", "CEO", "Apple");

			csvPrinter.printRecord(Arrays.asList("4", "Mark Zuckerberg", "CEO", "Facebook"));

			csvPrinter.flush();
		}
	}
}