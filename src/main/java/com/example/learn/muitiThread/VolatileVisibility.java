package com.example.learn.muitiThread;

public class VolatileVisibility{

	//private static  boolean initFlag = false;
	private static volatile boolean initFlag = false;

	public static void main(String[] args) throws InterruptedException{


		new Thread(new Runnable(){
			@Override
			public void run(){

				//java线程模型：每个线程对共享变量进行了复制，保存在 线程使用的cpu 的缓存里了,别的线程如果进行了修改，本线程是无法感知的，除非用volatile。
				// Volatile 让 cpu 缓存里的副本互相可见，原理就是，cpu 缓存不保存拷贝副本，直接去主存中访问
				System.out.println("waiting for the moment initFlag to be true.....");
				while(!initFlag){

				}
				// 如果initFlag 不是 volatile，则线程 1 永远无法感知线程 2 对 initflag 进行了修改
				System.out.println("initFlag is now \"true\"");
			}
		}).start();

		Thread.sleep(500);

		new Thread(new Runnable(){
			@Override
			public void run(){
				 initFlag = true;
			}
		}).start();


	}

}