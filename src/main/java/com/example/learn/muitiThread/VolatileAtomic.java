package com.example.learn.muitiThread;

public class VolatileAtomic{

  private static volatile Integer num = 0;

  private static void increase(){
  //private static synchronized void increase(){
    num++;
  }
  public static void main(String[] args) throws InterruptedException{

    Thread[] arrayThread = new Thread[10];
    for (int i = 0; i < arrayThread.length; i++){
      arrayThread[i] = new Thread(new Runnable(){
        @Override
        public void run(){
          for (int i = 0; i < 1000; i++){
            increase();
          }
        }
      });
      arrayThread[i].start();
    }

    for (int i = 0; i < arrayThread.length; i++){
      arrayThread[i].join();
    }
    System.out.println("num: " + num); // =====> 不是原子操作：  7678. 加入关键词 synchronized 可以保证得到 10000
  }

}