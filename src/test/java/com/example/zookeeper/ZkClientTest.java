package com.example.zookeeper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.Holder;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: demo
 * @description: zookeeper 的java基本操作 增删查改，以及监听数值
 * @author: Wei.Wang
 * @create: 2019-11-10 19:37
 **/


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@Slf4j
public class ZkClientTest{

  protected ZkClient zkClient;

  @Before
  public void setUp() throws Exception{
    zkClient = new ZkClient("192.168.0.129:2181",4000);
  }

  @After
  public void tearDown() throws Exception{
    zkClient.close();
  }

  @Test
  @DisplayName("测试建立和删除节点 createPersistent() delete()")
  public void testWriteAndRead(){
    String path = "/temp";
    zkClient.delete(path);
    String data = "something";
    zkClient.createPersistent(path,data);
    String data2 = zkClient.readData(path);
    Assert.assertEquals(data,data2);
    assertTrue(zkClient.exists(path));
    zkClient.delete(path);
  }


  @Test
  @DisplayName("给节点赋值 writeData()")
  public void testUpdateValue() throws Exception{
    String data = "something";
    String path = "/temp";
    if(!zkClient.exists(path)){
      zkClient.createPersistent(path,"");
    }
    zkClient.writeData(path,data);
    String data2 = zkClient.readData(path);
    Assert.assertEquals(data,data2);
  }


  @Test
  @DisplayName("异步监听 path 里的值被修改或者被删除，holder里的值被赋值或者被清除")
  public void testDataChanges() throws Exception{
    String path = "/temp";
    if(!zkClient.exists(path)){
      zkClient.createPersistent(path,"first data");
    }
    final Holder<String> holder = new Holder<String>();
    IZkDataListener listener = new IZkDataListener(){

      // 当 path 里的值被修改后，此处被执行，本地变量holder 得到回调函数返回的值 data
      @Override
      public void handleDataChange(String dataPath,Object data) throws Exception{
        log.error("监听到数据变动："+data);
        holder.set((String) data);
      }

      @Override
      public void handleDataDeleted(String dataPath) throws Exception{
        holder.set(null);
      }
    };
    log.error("向节增加监听");
    zkClient.subscribeDataChanges(path,listener);
    log.error("向节点写入数据："+"new data");
    zkClient.writeData(path,"new data"); // 修改 path 节点里的值

    // 启动一个线程 4 秒后获取最新的值，为什么要等待 4 秒？ 防止事件还没有被触发
    String contentFromHolder =waitUntil(new Callable<String>(){
      @Override
      public String call() throws Exception{
        return holder.get();
      }
    },TimeUnit.SECONDS,2);

    assertEquals("new data",contentFromHolder);
  }

  public static <T> T waitUntil(Callable<T> callable, TimeUnit timeUnit, long timeout) throws Exception {
    long startTime = System.currentTimeMillis();
    do {
      T actual = callable.call();
      if (System.currentTimeMillis() > startTime + timeUnit.toMillis(timeout)) {
        return actual;
      }
      Thread.sleep(50);
    } while (true);
  }
}

