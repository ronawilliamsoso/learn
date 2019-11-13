package com.example.learn.kafka;

/**
 * @program: demo
 * @description:
 * @author: Wei.Wang
 * @create: 2019-11-13 18:07
 **/


import com.example.learn.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderSender {

  @Autowired
  private KafkaTemplate<String,Order> kafkaTemplate;

  private String topic = "orders";

  public void send(Order data){
    log.error("sending data='{}' to topic='{}'", data, topic);

    Message<Order> message = MessageBuilder
        .withPayload(data)
        .setHeader(KafkaHeaders.TOPIC, topic)
        .build();
    kafkaTemplate.send(message);
  }
}
