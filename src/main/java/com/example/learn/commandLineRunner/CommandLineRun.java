package com.example.learn.commandLineRunner;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private CacheManager cacheManager;

  @Override
  public void run(String... args) throws Exception{
    System.out.println("CommandLineRunner: 程序启动了，测试一下 ehcache 是否可用");

    Cache<String, String> mineCache = cacheManager.getCache("defaultCache",String.class,String.class);
    String strTemp = "放入 ehcache 里的字符串测试数据";
    mineCache.put("key", strTemp);
    System.out.println( "从 ehcache 里取出的测试数据:" + mineCache.get("key"));
  }
}

