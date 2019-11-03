package com.example.demo.muitiThread;

public class VolatileAtomic_Synchronized{

  private static volatile Integer num = 0;


  private static synchronized void increase(){
    num++;
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
    System.out.println("最终结果num: " + num); // =====> synchronized 保证原子操作， volatile保证可见性，所以结果是 100000
  }

}