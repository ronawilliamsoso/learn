package com.example.learn.muitiThread.AbstractQueuedSynchronized.sharedLock;

// Semaphore是一种计数信号量，用于管理一组资源，内部是基于AQS的共享模式。它相当于给线程规定一个量从而控制允许活动的线程数。

import java.util.concurrent.Semaphore;
import lombok.SneakyThrows;


public class SemaphoreLimit{


  public static void main(String[] args){

    Semaphore semaphore = new java.util.concurrent.Semaphore(2,true);

    for (int i = 0; i < 10; i++){
      new Thread(new Access(semaphore, "小豆豆"+(i+1))).start();
    }
  }

  public  static class Access extends Thread{
    Semaphore semaphore;

    public Access(Semaphore semaphore, String threadName){
      this.semaphore = semaphore;
      this.setName(threadName);
    }

    @SneakyThrows
    public void run(){

      semaphore.acquire();
      System.out.println("线程【"+this.getName()+"】获得访问权");
      Thread.sleep(3000);
      semaphore.release();
    }
  }

}
