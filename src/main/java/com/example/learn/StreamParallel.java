package com.example.learn;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * @author: shuang.gao Date: 2015/7/14 Time: 8:16
 */
public class StreamParallel {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ArrayList<Long> array = genArray(10000000);
		// calculateByFor(array);
		// calculateByStream(array);
		// calculateByStreamParallel(array);

	}

	private static void calculateByFor(ArrayList<Long> array) {
		long begin = System.currentTimeMillis();

		for (int i = 0; i < array.size(); i++) {
			array.set(i, array.get(i) + 1);
		}
		long end = System.currentTimeMillis();
		System.out.println(String.format("单线程for 循环耗时 %sms", end - begin));

	}

	private static void calculateByStream(ArrayList<Long> array) {
		long begin = System.currentTimeMillis();

		array.stream().forEach(m -> {
			// System.out.println("Thread : " +
			// Thread.currentThread().getName());
			m = m + 1;
		});
		long end = System.currentTimeMillis();
		System.out.println(String.format("stream for each 耗时 %sms", end - begin));

	}

	private static void calculateByStreamParallel(ArrayList<Long> array) {
		// List<Long> list = Collections.synchronizedList(array);

		long begin = System.currentTimeMillis();

		array.parallelStream().forEach(m -> {
			// System.out.println("Thread : " +
			// Thread.currentThread().getName());
			m = m + 1;
		});
		long end = System.currentTimeMillis();
		System.out.println(String.format("并行流 for each 耗时 %sms", end - begin));

	}

	private static ArrayList<Long> genArray(int size) {

		ArrayList<Long> arrayList = new ArrayList<Long>();
		for (int i = 0; i < size; i++) {
			arrayList.add(new Random().nextLong());
		}
		return arrayList;
	}
}
