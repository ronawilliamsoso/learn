package com.example.learn.kafka;

import com.example.learn.model.Order;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @program: lern
 * @description:  发送一个消息到 kafka，console 里会打印出这个消息
 * @author: Wei.Wang
 * @create: 2019-11-11 16:52
 **/

@RestController
@RequestMapping("/kafka")
public class KafkaProducerController{

	@Autowired
	public KafkaTemplate<String,Object> kafkaTemplate;

	@ApiOperation(value = "send a message to kafka")
	@GetMapping("/message/send")
	public boolean send(@RequestParam String message){
		kafkaTemplate.send("topic1",message);
		return true;
	}

	@ApiOperation(value = "send an object to kafka")
	@GetMapping("/message/sendObj")
	public boolean sendObj(){

		Order order = Order.builder().productId(1).userId("wang").orderId(111).build();
		kafkaTemplate.send("topic1",order);
		return true;
	}
}
