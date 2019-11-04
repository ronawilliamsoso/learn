package com.example.learn.muitiThread.AbstractQueuedSynchronized.sharedLock;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import lombok.SneakyThrows;


/**
 * 主线程等待子线程执行完成再执行, CyclicBarrier 是可以多次使用的。与 CountDownLatch  的区别是，等待结束后执行任务的是子线程，相当于所有子线程一起做某件事情。
 */
public class CylicBarrierExample{

  public static void main(String[] args){
    CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
    for (int i = 0; i < cyclicBarrier.getParties(); i++){
      new Thread(new SubTask(cyclicBarrier, "小豆豆"+(i+1))).start();
    }
    System.out.println("船靠岸，等待乘客一起过河");
  }


  static class SubTask    extends Thread {
    CyclicBarrier cyclicBarrier;

    public SubTask(CyclicBarrier cyclicBarrier, String threadName){
      this.setName(threadName);
      this.cyclicBarrier = cyclicBarrier;
    }
    @SneakyThrows
    public void run(){
      System.out.println("乘客【"+this.getName()+"】登船，等待其他乘客一起过河");
      Random rand = new Random();
      int randomNum = rand.nextInt((6000 - 1000) + 1) + 1000;
      Thread.sleep(randomNum);
      cyclicBarrier.await();
      System.out.println("乘客【"+this.getName()+"】通过了第一条河");
    }
  }
}
