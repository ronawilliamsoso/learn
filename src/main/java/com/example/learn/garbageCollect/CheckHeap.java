package com.example.learn.garbageCollect;

import lombok.SneakyThrows;

/**
 * @program: demo
 * @description: 通过命令查看 heap 里的东西，以及gc详情
 *jvm 启动参数 ： -XX:+PrintGCDetails 可以打印类似于：
 * garbage-first heap   total 2062336K, used 1801805K
 *
 * @author: Wei.Wang
 * @create: 2019-11-05 23:11
 **/

public class CheckHeap{

  @SneakyThrows
  public static void main(String[] args){
    byte [] allocation1  = new byte[600000*1024];
    byte [] allocation2  = new byte[600000*1024];
    byte [] allocation3  = new byte[600000*1024];

    Thread.sleep(Integer.MAX_VALUE);
  }
}

