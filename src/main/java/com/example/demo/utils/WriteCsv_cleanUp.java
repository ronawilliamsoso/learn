package com.example.demo.utils;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import lombok.Cleanup;

public class WriteCsv_cleanUp{
	private static final String SAMPLE_CSV_FILE = "/Users/wangwei/Documents/workspace/learning/demo/src/main/java/com/example/demo/file/wcar.csv";

	public static void main(String[] args) throws IOException {
		@Cleanup OutputStream out = new FileOutputStream(SAMPLE_CSV_FILE);
		byte  bytes =  Byte.parseByte("write into a file");
		out.write(bytes);
	}
}