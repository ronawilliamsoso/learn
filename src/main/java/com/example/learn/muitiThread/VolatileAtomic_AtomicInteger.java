package com.example.learn.muitiThread;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileAtomic_AtomicInteger{

  private static AtomicInteger   num =  new AtomicInteger(0);

  private static  void increase(){
    num.getAndIncrement();
  }

  public static void main(String[] args) throws InterruptedException{
    long begin = System.currentTimeMillis();
    Thread[] arrayThread = new Thread[10];
    for (int i = 0; i < arrayThread.length; i++){
      arrayThread[i] = new Thread(new Runnable(){
        @Override
        public void run(){
          for (int i = 0; i < 100000; i++){
            increase();
          }
        }
      });
      arrayThread[i].start();
    }

    for (int i = 0; i < arrayThread.length; i++){
      arrayThread[i].join();
    }

    long end = System.currentTimeMillis();
    System.out.println("所花时间： " +(end - begin  ));
    System.out.println("最终结果num: " + num); // =====> getAndIncrement()是原子操作,比 synchronized 快 一倍以上

  }

}