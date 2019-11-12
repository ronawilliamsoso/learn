package com.example.learn.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @program: demo
 * @description: ConsumerListener
 * @author: Wei.Wang
 * @create: 2019-11-12 17:35
 **/


@Component
@Slf4j
public class ConsumerListener{

  @KafkaListener(topics = "topic1")
  public void onMessage(String message){
    //insertIntoDb(buffer);//这里为插入数据库代码
    log.error("kafka收到消息： "+message);
  }
}

