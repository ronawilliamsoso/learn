package com.example.demo.utils;


import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.Cleanup;
import lombok.SneakyThrows;

public class ReadCsv_sneakythrow{

	@SneakyThrows
	public static void main(String[] args) {
		  //当绝对不可能出现FileNotFoundException的时候，将编译阶段可能出现的 FileNotFoundException 放到运行时，代码简洁更多,但是使用要谨慎
		  final String SAMPLE_CSV_FILE_PATH = "/Users/wangwei/Documents/workspace/learning/demo/src/main/java/com/example/demo/file/car.csv";
			@Cleanup Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));

	}
}