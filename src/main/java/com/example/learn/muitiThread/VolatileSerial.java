package com.example.learn.muitiThread;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VolatileSerial{


	private static   int x = 0 , y=0;
	private static   int a = 0, b=0;
	static  Object object = new Object();

	public static void main(String[] args) throws InterruptedException{
		Map<String,Integer> resultMap = new HashMap<>();

		Set<String> resultSet = new HashSet<>();


		for (int i = 0; i <10000 ; i++){
			resultMap.clear();
			x=0;y=0;

			Thread tone = new Thread(new Runnable(){
				@Override
				public void run(){
					a= y;
					x=1;
					resultMap.put("a", a);
				}
			});


			Thread ttwo = new Thread(new Runnable(){

				@Override
				public void run(){
					b = x;
					y=1;
					resultMap.put("b", b);
				}
			});

			tone.start();
			Thread.sleep(10);

			ttwo.start();

			tone.join();
			ttwo.join();

			if(resultMap.get("a").intValue()==1  && resultMap.get("b").intValue() ==1){
				System.out.println("奇怪");
			}



		}

		System.out.println("结束");



	}

}