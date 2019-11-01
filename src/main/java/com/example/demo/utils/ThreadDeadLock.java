package com.example.demo.utils;

import lombok.SneakyThrows;

public class ThreadDeadLock{


	// 用 JPS 可以查看正在运行的 java pid
	// jstack pid 可以查看进程的信息
	private static   String resource_a = "a";
	private static   String resource_b = "b";

	public static void main(String[] args)  {
		deadLock();

	}

	@SneakyThrows
	public static void deadLock(){

		Thread one = new Thread(new Runnable(){
			@Override
			public void run(){
				synchronized (resource_a){
					System.out.println("one: a is here");
					try{
						Thread.sleep(1000);
						synchronized (resource_b){
							System.out.println("one: b is here");
						}
					}catch (InterruptedException e){
						e.printStackTrace();
					}
				}
			}
		});


		Thread two = new Thread(new Runnable(){
			@Override
			public void run(){
				synchronized (resource_b){
					System.out.println("two: b is here");
					try{
						Thread.sleep(1000);
						synchronized (resource_a){
							System.out.println("two: a is here");
						}
					}catch (InterruptedException e){
						e.printStackTrace();
					}
				}

			}
		});

		one.start();
		two.start();
	}

}