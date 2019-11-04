package com.example.learn.muitiThread.AbstractQueuedSynchronized.sharedLock;

import java.util.concurrent.CountDownLatch;
import lombok.SneakyThrows;

/**
 * 主线程等待子线程执行完成再执行, countDownLatch是一次性使用的。与 CyclicBarrier 的区别是，等待结束后执行任务的只有主线程。
 */
public class CountDownLatchExample{

  @SneakyThrows
  public static void main(String[] args){
    Integer THREADNUMBER = 5;
    CountDownLatch  countDownLatch = new CountDownLatch(THREADNUMBER);

    for (int i = 0; i < THREADNUMBER; i++){
      new Thread(new SubTask(countDownLatch,"小豆豆"+(i+1))).start();
    }
    System.out.println("主线程【"+Thread.currentThread().getName()+"】什么都不做，只是等待所有子线程");
    countDownLatch.await();
    System.out.println("所有子线程执行完毕，主线程【"+Thread.currentThread().getName()+"】开始做自己的事情");
  }


  static class SubTask extends Thread{
    CountDownLatch  countDownLatch;
    SubTask(CountDownLatch countDownLatch,String ThreadName){
      this.setName(ThreadName);
      this.countDownLatch = countDownLatch;
    }

    @SneakyThrows
    public void run(){

      System.out.println("子线程【"+this.getName()+"】开始执行.........");
      Thread.sleep(2000);
      System.out.println("子线程【"+ this.getName() +"】执行完毕。");
      countDownLatch.countDown();
    }

  }

}
