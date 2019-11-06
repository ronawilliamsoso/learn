package com.example.learn.muitiThread.AbstractQueuedSynchronized.concurrentMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: demo
 * @description: 一个让 concurrentMap 不安全的例子
 * @author: Wei.Wang
 * @create: 2019-11-06 16:16
 **/

@Slf4j
public class concurrentMapExample{

  @SneakyThrows
  public static void main(String[] args){

    ConcurrentHashMap<String,AtomicInteger> concurrentHashMap = new ConcurrentHashMap<String,AtomicInteger>();
    ExecutorService executorService = Executors.newFixedThreadPool(100);
    AtomicInteger value = new AtomicInteger(0);
    concurrentHashMap.put("key",value );
    for (int i = 0; i < 1000; i++){
      executorService.execute(new Runnable(){
        @Override
        public void run(){
          concurrentHashMap.get("key").incrementAndGet();
        }
      });
    }
    executorService.shutdown();
    log.error("使用了线程安全的AtomicInteger，结果必须是 1000："+concurrentHashMap.get("key"));
  }
}

