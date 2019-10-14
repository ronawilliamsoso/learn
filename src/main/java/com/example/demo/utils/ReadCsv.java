package com.example.demo.utils;


import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ReadCsv {

	public static void main(String[] args) {


		  final String SAMPLE_CSV_FILE_PATH = "/Users/wangwei/Documents/workspace/learning/demo/src/main/java/com/example/demo/file/car.csv";


				try (
						Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
						CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
								.withHeader("Name", "Email", "Phone", "Country")
								.withIgnoreHeaderCase()
								.withTrim())
				) {
					for (CSVRecord csvRecord : csvParser) {
						// Accessing values by the names assigned to each column
						String name = csvRecord.get("Name");
						String email = csvRecord.get("Email");
						String phone = csvRecord.get("Phone");
						String country = csvRecord.get("Country");

						System.out.println("Record No - " + csvRecord.getRecordNumber());
						System.out.println("---------------");
						System.out.println("Name : " + name);
						System.out.println("Email : " + email);
						System.out.println("Phone : " + phone);
						System.out.println("Country : " + country);
						System.out.println("---------------\n\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}


	}
}