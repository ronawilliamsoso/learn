package com.example.learn.muitiThread.AbstractQueuedSynchronized.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;
import lombok.SneakyThrows;


// AtomicIntegerArray本质上是对int[]类型的封装。使用Unsafe类通过CAS的方式控制int[]在多线程下的安全性。
// 本例的结果是一个 10 个元素每个元素都为 10000 的数组，如果不是原子操作，不一定每个元素都是 10000
public class AtomicIntegerArrayExample{

  static Integer arrayLength = 10;
  static AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(arrayLength);
  static class AddTask implements Runnable{
    @Override
    public void run(){
      for (int i = 0; i < 10000; i++){
        atomicIntegerArray.getAndIncrement(i%arrayLength);// 将第 i 个元素+1
      }
    }
  }

  @SneakyThrows
  public static void main(String[] args){

    Thread[] ts = new Thread[arrayLength];
    for (int i = 0; i <arrayLength ; i++){
      ts[i] = new Thread(new AddTask());

      ts[i].start();
    }
    for(int k=0;k<10;k++){ts[k].join();}
    System.out.println("结果为全部都是 10000 的数组： "+atomicIntegerArray);
  }
}
