package com.example.learn.kafka;

import com.example.learn.model.Order;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @program: lern
 * @description:  发送一个消息到 kafka，console 里会打印出这个消息
 * @author: Wei.Wang
 * @create: 2019-11-11 16:52
 **/

@RestController
@RequestMapping("/message")
public class KafkaProducerController{

	@Autowired
	private OrderSender sender;

	@ApiOperation(value = "send an object to kafka")
	@GetMapping("/send/order")
	public void sendObj(){
		Order order = Order.builder().productId(1).userId("wang").build();
		sender.send(order);
	}
}
