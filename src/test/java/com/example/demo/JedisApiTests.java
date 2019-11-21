package com.example.demo;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;


/**
 * @program: demo
 * @description: how to use jedis
 * @author: Wei.Wang
 * @create: 2019-11-21 17:59
 **/

public class JedisApiTests{

  private Jedis jedis;
  private static  final String KEY="20181212";
  private static  final String VALUE="weiwang";

  @Before
  public void setup(){
    jedis = new Jedis("127.0.0.1",6379);
  }



  @Test
  public void testKeys(){
    System.out.println("清空数据："+jedis.flushDB());
    System.out.println("判断某个键是否存在："+jedis.exists("username"));
    System.out.println("新增<'username','zzh'>的键值对："+jedis.set("username", "zzh"));
    Set<String> keys = jedis.keys("*");
    System.out.println("系统中所有的键如下："+keys);
    System.out.println("删除键password:"+jedis.del("username"));
    System.out.println("查看键username所存储的值的类型："+jedis.type("username"));

  }


  @Test
  public void testTtl() {
    long ttl = jedis.ttl(KEY);
    System.out.println("倒计时时间 :" + ttl);
  }

  /***
   * 剩余时间 秒
   */
  @Test
  public void testTtl2() {
    long ttl = jedis.expire(KEY,100);
    System.out.println("倒计时时间 :" + ttl);
  }
  /***
   * 剩余时间 毫秒
   * 区别 单位不一样
   */

  @Test
  public void testPttl() {
    long pttl = jedis.pttl(KEY);
    System.out.println("pttl is :" + pttl);
  }

  /***
   * 数据类型
   */
  @Test
  public void testType() {
    String type = jedis.type(KEY);
    System.out.println("数据类型 :" + type);
  }

  /***
   *判断数据是否存在
   */
  @Test
  public void testExists() {
    boolean flag = jedis.exists(KEY);
    System.out.println("是否存在:" + flag);
  }

  /***
   * 删除
   */
  @Test
  public void testDelete() {

    System.out.println("data exists:" + jedis.del(KEY));
  }


  @Test
  public void testString(){
    jedis.flushDB();
    System.out.println(jedis.set("key3", "value3"));
    System.out.println("在key3后面加入值："+jedis.append("key3", "End"));
    System.out.println("key3的值："+jedis.get("key3"));
    System.out.println("增加多个键值对："+jedis.mset("key01","value01","key02","value02","key03","value03"));
    System.out.println("获取多个键值对："+jedis.mget("key01","key02","key03"));
    System.out.println("获取多个键值对："+jedis.mget("key01","key02","key03","key04"));
    System.out.println("删除多个键值对："+jedis.del(new String[]{"key01","key02"}));
    System.out.println("获取多个键值对："+jedis.mget("key01","key02","key03"));
    jedis.flushDB();
    System.out.println("===========新增键值对防止覆盖原先值==============");
    System.out.println(jedis.setnx("key1", "value1"));//判断下是否存在key
    System.out.println(jedis.setnx("key2", "value2"));
    System.out.println(jedis.setnx("key2", "value2-new"));
    System.out.println(jedis.get("key1"));
    System.out.println(jedis.get("key2"));

    System.out.println("===========获取原值，更新为新值==========");//GETSET is an atomic set this value and return the old value command.
    System.out.println(jedis.getSet("key2", "key2GetSet"));
    System.out.println(jedis.get("key2"));
  }
  @Test
  public void testList() {
    jedis.flushDB();
    System.out.println("===========添加一个list===========");
    jedis.lpush("collections", "wukong", "zhangsan", "wangwu", "xiaoming", "xiaoer", "tangseng");
    System.out.println("collections的内容："+jedis.lrange("collections", 0, -1));
    System.out.println("collections的长度："+jedis.llen("collections"));
    jedis.lpush("sortedList排序后", "3","6","2","0","7","4");
    System.out.println(jedis.sort("sortedList排序后"));
    System.out.println("sortedList排序后："+jedis.lrange("sortedList排序后", 0, -1));
  }


  /***
   * 键值对方式
   */
  @Test
  public void testMset(){
    jedis.mset(new String[]{"name", "wukong", "age", "18", "qq", "245553999"});
    System.out.printf("姓名：%s,年龄：%s，联系方式：%s",jedis.get("name") , jedis.get("age") ,jedis.get("qq"));
  }
  /***
   * 原子操作 实用点 分布式的原子 JUC Atomic* JVM
   * 接口幂等性 也是用用
   * exprie超时时间（一周） 做开发
   */
  @Test
  public void testIncr(){
    jedis.incr("age");
    System.out.println(jedis.get("age"));
  }

  @Test
  public void testSet() {
    jedis.flushDB();
    System.out.println("============向集合中添加元素============");
    System.out.println(jedis.sadd("eleSet", "e1", "e2", "e4", "e3", "e0", "e8", "e7", "e5"));
    System.out.println(jedis.sadd("eleSet", "e6"));
    System.out.println(jedis.sadd("eleSet", "e6"));//重复问题 1 or 0
    System.out.println("eleSet的所有元素为：" + jedis.smembers("eleSet"));

    System.out.println("=================================");
    System.out.println(jedis.sadd("eleSet1", "e1","e2","e4","e3","e0","e8","e7","e5"));
    System.out.println("============集合运算=================");
    System.out.println("eleSet和eleSet1的交集:"+jedis.sinter("eleSet","eleSet1"));
    System.out.println("eleSet和eleSet1的并集:"+jedis.sunion("eleSet","eleSet1"));
    System.out.println("eleSet和eleSet1的差集:"+jedis.sdiff("eleSet","eleSet1"));//eleSet1中有，eleSet2中没有
  }


  /***
   * 散列
   */
  @Test
  public void testHash() {
    jedis.flushDB();
    Map<String, String> map = new HashMap<String, String>();
    map.put("zhangsan", "a");
    map.put("canglaoshi", "b");
    map.put("wukong", "c");
    map.put("wangwu", "d");
    jedis.hmset("hash", map);
    jedis.hset("hash", "luban", "e");
    System.out.println("散列hash的所有键值对为：" + jedis.hgetAll("hash"));//return Map<String,String>
    System.out.println("散列hash的所有键为：" + jedis.hkeys("hash"));//return Set<String>
    System.out.println("散列hash的所有值为：" + jedis.hvals("hash"));//return List<String>

  }

  /***
   * 排序 有点用
   */
  @Test
  public void testSortedSet() {
    jedis.flushDB();
    Map<String, Double> map = new HashMap<String, Double>();
    map.put("zhangsan", 1.2);
    map.put("canglaoshi", 4.0);
    map.put("wukong", 5.0);
    map.put("wangwu", 0.2);
    System.out.println(jedis.zadd("zset", 3, "wukong"));
    System.out.println(jedis.zadd("zset", map));
    System.out.println("zset中的所有元素：" + jedis.zrange("zset", 0, -1));
    System.out.println("zset中wukong的分值：" + jedis.zscore("zset", "wukong"));
    System.out.println("zset中wukong的排名：" + jedis.zrank("zset", "wukong"));

  }

}

