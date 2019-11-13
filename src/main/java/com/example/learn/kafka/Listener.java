package com.example.learn.kafka;

import com.example.learn.model.Order;
import com.example.learn.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@AllArgsConstructor
public class Listener{

  private OrderRepository orderRepository;

  private ThreadPoolTaskExecutor threadPoolTaskExecutor;

  @KafkaListener(topics = "orders")
  public void onMessage(@Payload Order order){
    log.error("监听 Order 的kafka监听器收到消息： " + order);
     threadPoolTaskExecutor.execute(() -> orderRepository.save(order));
  }
}

