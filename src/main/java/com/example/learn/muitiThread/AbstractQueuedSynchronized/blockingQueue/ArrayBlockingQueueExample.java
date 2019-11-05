package com.example.learn.muitiThread.AbstractQueuedSynchronized.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

// 阻塞队列的意思是，当放入的东西数量超过容量，"放"这个动作被阻塞，"拿"这个动作被激活，队列为空的时候，"拿"被阻塞，"放"被激活

// 本例中，假如ArrayBlockingQueue是一个可以放入两个球的盒子，放球的动作每秒一次，拿球的动作每5秒一次，结果就是，一开始可以放入两个球，
// 超过两个之后，后面放球的动作一直被拿球的动作阻塞，无法持续的放入超过两个球。


@Slf4j
public class ArrayBlockingQueueExample{

  private static ArrayBlockingQueue<Ball> box = new ArrayBlockingQueue<>(2,true );


  public static void main(String[] args){
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

    Thread putBallThread = new Thread(new Runnable(){
      @Override
      @SneakyThrows
      public void run(){
        Ball ball =  Ball.builder().ballNumber(randomInt()).build();
        box.put(ball);
        log.warn("放入一个球,球号： "+ball.getBallNumber() );
      }
    });

    Thread getBallThread = new Thread(new Runnable(){
      @Override
      @SneakyThrows
      public void run(){
        Ball ball = box.take();
       log.error("取出一个球,球号："+ ball.getBallNumber());
      }
    });


    scheduledExecutorService.scheduleAtFixedRate(putBallThread,0,1,TimeUnit.SECONDS);
    scheduledExecutorService.scheduleAtFixedRate(getBallThread,0,5,TimeUnit.SECONDS);

  }

  private static Integer randomInt(){
    return ThreadLocalRandom.current().nextInt(100);
  }

}
