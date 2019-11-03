package com.example.demo.muitiThread.AbstractQueuedSynchronized.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

///////////////////////////////////////////////////////////////////////////
// ReentrantLock 是独占锁
///////////////////////////////////////////////////////////////////////////

public class RepeatLock{

  private int criticalResource = 0;

  private ReentrantLock lock = new ReentrantLock(true);

  void dosomething(String threadName){

    System.out.println("线程【"+threadName+"】准备加锁,lock state：0");

    lock.lock();
    System.out.println("线程【"+threadName+"】第1次加锁，lock state：1");
    criticalResource ++;


    lock.lock();
    System.out.println("线程【"+threadName+"】第2次加锁,lock state：2");
    criticalResource ++;

    lock.unlock();
    System.out.println("线程【"+threadName+"】第1次释放锁,lock state：1");

    lock.unlock();
    System.out.println("线程【"+threadName+"】第2次释放锁,lock state：0");

    System.out.println("criticalResource: "+ criticalResource);


  }



  public static void main(String[] args){

    RepeatLock repeatLock = new RepeatLock();

    for (int i = 0; i < 10000; i++){

      new Thread(() -> {
        String threadName = Thread.currentThread().getName();
        repeatLock.dosomething(threadName);
      },"小豆豆"+i).start();
    }
  }
}
