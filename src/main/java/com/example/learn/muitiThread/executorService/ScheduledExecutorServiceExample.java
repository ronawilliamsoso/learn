package com.example.learn.muitiThread.executorService;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//ScheduledExecutorService 可以用来做定时调度任务
//1.newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
// 2.newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
// 3.newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
// 4.newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
public class ScheduledExecutorServiceExample{

  public static void main(String[] args){

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    Thread thread = new Thread(new Runnable(){
      @Override
      public void run(){
        log.warn("永不退出，延迟 1 秒，每隔 3 秒执行一次间隔执行");
      }
    });

    scheduledExecutorService.scheduleAtFixedRate(thread,1,3,TimeUnit.SECONDS);
  }

}
