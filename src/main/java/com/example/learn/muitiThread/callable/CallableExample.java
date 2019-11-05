package com.example.learn.muitiThread.callable;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import lombok.SneakyThrows;

//﻿Runnable没有返回值；Callable可以返回执行结果，可以查看是否失败，可以 cancel 一个线程，返回结果是个泛型
public class CallableExample{

  @SneakyThrows
  public static void main(String[] args){

    ExecutorService executorService = Executors.newCachedThreadPool();

    List<FutureTask<AverageSalary>> futureTasks = new ArrayList<FutureTask<AverageSalary>>();
    for (int i = 0; i < 10; i++){
      CalculateSheetCallable calculateSheetCallable = new CalculateSheetCallable("小豆豆"+i,i);
      FutureTask<AverageSalary> averageSalaryFutureTask = new FutureTask<>(calculateSheetCallable);
      executorService.submit(averageSalaryFutureTask);
      futureTasks.add(averageSalaryFutureTask);
    }

    System.out.println("主线程等待子线程异步执行各自的任务.......");
    System.out.println();
    Thread.sleep(2000);

    executorService.shutdown();
    System.out.println();
    System.out.println("主线程发布最终结果：");
    System.out.println();
    for (FutureTask<AverageSalary> averageSalaryFutureTask:futureTasks ){

      System.out.println("子线程完成状态："+averageSalaryFutureTask.isDone()+" 计算结果："+ averageSalaryFutureTask.get() );

    }

  }
}
