package com.example.learn.muitiThread.AbstractQueuedSynchronized.concurrentMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: demo
 * @description: 一个让 concurrentMap 不安全的例子
 * @author: Wei.Wang
 * @create: 2019-11-06 16:16
 **/

@Slf4j
public class concurrentMapNotSafe{

  @SneakyThrows
  public static void main(String[] args){

    ConcurrentHashMap<String,Integer> concurrentHashMap = new ConcurrentHashMap<String,Integer>();
    ExecutorService executorService = Executors.newFixedThreadPool(100);
    concurrentHashMap.put("key",0 );
    for (int i = 0; i < 1000; i++){
      executorService.execute(new Runnable(){
        @Override
        public void run(){
          int value = concurrentHashMap.get("key")+1;
          concurrentHashMap.put("key", value);
        }
      });
    }
    executorService.shutdown();
    log.error("最终结果如果不是 1000 就是线程不安全："+concurrentHashMap.get("key"));
  }
}

