package com.example.learn.garbageCollect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @program: demo
 * @description: 程序启动后 启动一个耗费资源的程序
 * @author: Wei.Wang
 * @create: 2019-11-14 21:02
 **/

@Component
@Slf4j
public class StartGcAnysis implements CommandLineRunner{

  @Autowired
  private RestTemplate restTemplate;

  @Override
  @Order(2)
  public void run(String... args) throws Exception{
    System.out.println("start a gc program using a lot of memory");

    for (int i = 0; i < 600; i++) {
      String result = restTemplate.getForObject("http://localhost:8080/gc/user", String.class);
      Thread.sleep(1000);
    }

    log.error("结束，可以开始分析 log 文件");

  }
}

