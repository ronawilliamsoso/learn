package com.example.learn.config;

import java.util.concurrent.TimeUnit;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @program: demo
 * @description:
 * @author: Wei.Wang
 * @create: 2019-11-14 22:19
 **/

@Configuration
@EnableCaching
public class EhcacheConfig{
  private static CacheManager cacheManager;

  @Bean
  public CacheManager ehCacheManager() {
    System.out.println("[Ehcache配置初始化<开始>]");

    // 配置默认缓存属性
    CacheConfiguration<String, String> cacheConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(
        // 缓存数据K和V的数值类型
        // 在ehcache3.3中必须指定缓存键值类型,如果使用中类型与配置的不同,会报类转换异常
        String.class,String.class,
        ResourcePoolsBuilder
            .newResourcePoolsBuilder()
            .heap(1000L,EntryUnit.ENTRIES) //设置缓存堆容纳元素个数(JVM内存空间)超出个数后会存到offheap中
            .offheap(100L, MemoryUnit.MB) //设置堆外储存大小(内存存储) 超出offheap的大小会淘汰规则被淘汰
            .disk(500L, MemoryUnit.MB, false) // 配置磁盘持久化储存(硬盘存储)用来持久化到磁盘,这里设置为false不启用
    ).withExpiry(Expirations.timeToLiveExpiration(
        Duration.of(30L, TimeUnit.SECONDS)) //设置缓存过期时间
    ).withExpiry(Expirations.timeToIdleExpiration(
        Duration.of(60L, TimeUnit.SECONDS)) //设置被访问后过期时间(同时设置和TTL和TTI之后会被覆盖,这里TTI生效,之前版本xml配置后是两个配置了都会生效)
    ).build();

    cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                                      .with(CacheManagerBuilder.persistence("~/ehcacheData")) // 硬盘持久化地址
                                      .withCache("defaultCache", cacheConfiguration) // 设置一个默认缓存配置
                                      .build(true);
    return cacheManager;
  }


}

