package com.example.learn.muitiThread.callable;


import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;


public class CalculateSheetCallable implements Callable<AverageSalary>{

  private String threadName;
  private Integer sheetNumber;

  public CalculateSheetCallable(String threadName,Integer sheetNumber){
    this.threadName = threadName;
    this.sheetNumber = sheetNumber;
  }
  @Override
  public AverageSalary call() throws Exception{
    AverageSalary averageSalary = AverageSalary.builder().number(randomInteger()).averageSalary(randomDouble()).build();
    System.out.println("线程【"+ this.threadName+"】计算第"+sheetNumber+"页的平均工资");
    return averageSalary;
  }


  private Integer randomInteger() {
    return ThreadLocalRandom.current().nextInt(1000);
  }

  private Double randomDouble() {
    return ThreadLocalRandom.current().nextDouble(5000);
  }

}
