package com.example.learn.commandLineRunner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @program: demo
 * @description: 程序启动后做一些事情
 * @author: Wei.Wang
 * @create: 2019-11-14 21:02
 **/

@Component
public class CommandLineRun implements CommandLineRunner{

  @Override
  public void run(String... args) throws Exception{
    System.out.println("CommandLineRunner: 程序启动了");
  }
}

