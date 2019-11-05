package com.example.learn.muitiThread.AbstractQueuedSynchronized.exclusiveLock_reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

///////////////////////////////////////////////////////////////////////////
// ReentrantLock 是独占锁 可重入锁 这个例子就是重复加锁两次，然后两次开锁
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
