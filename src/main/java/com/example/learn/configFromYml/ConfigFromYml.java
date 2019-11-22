package com.example.learn.configFromYml;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @program: demo
 * @description: 程序启动后 启动一个耗费资源的程序
 * @author: Wei.Wang
 * @create: 2019-11-14 21:02
 **/

@Component
@Slf4j
public class ConfigFromYml implements CommandLineRunner{

  @Autowired
  private YmlBeanPerson ymlBeanPerson;

  @Override
  @Order(3)
  public void run(String... args) throws Exception{
    System.out.println("读取配置文件里的参数：");
    log.error(ymlBeanPerson.getName());
    log.error(""+ymlBeanPerson.getGirlfriend().getAge());

  }
}

