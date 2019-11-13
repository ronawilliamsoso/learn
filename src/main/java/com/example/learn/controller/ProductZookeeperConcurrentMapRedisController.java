package com.example.learn.controller;

import com.example.learn.model.Order;
import com.example.learn.model.Product;
import com.example.learn.repository.ProductRepository;
import com.example.learn.utils.RedisUtils;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: demo
 * @description: 秒杀商品防止超卖，当类被加载到容器之后将秒杀产品读取到 redis,  秒杀的时候，商品卖完的标记，直接放入内存 能阻挡绝大部分访问
 *               redisUtils.decr   redisUtils.incr 是原子操作，单线程串行执行，所以线程安全
 *               zookeeper 用于同步多个节点之间的 concurrentmap 里的值
 * @author: Wei.Wang
 * @create: 2019-11-11 16:52
 **/


@RestController
@RequestMapping("/productZookeeperRedisConcurrentMap")
@Slf4j
public class ProductZookeeperConcurrentMapRedisController{

  public static final String PRODUCT_STOCK = "product_stock_redis_zookeeper";

  public static final String PRODUCT_SOLDOUT_Path = "/product_soldout";


  private static ConcurrentHashMap<Integer,Boolean> productSoldOutMap = new ConcurrentHashMap<>();

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  RedisUtils redisUtils;

  @Autowired
  public KafkaTemplate<String,Object> kafkaTemplate;


  protected ZkClient zkClient   = new ZkClient("192.168.0.129:2181",4000);


  /**
   * 当类被加载到容器之后就会执行的一个方法
   **/
  @PostConstruct
  public void init(){
    List<Product> products = productRepository.findAll();
    for (Product product : products){
      redisUtils.set(PRODUCT_STOCK + "_" + product.getId(),product.getProductStock());
      if(!zkClient.exists(PRODUCT_SOLDOUT_Path)){
        zkClient.createPersistent(PRODUCT_SOLDOUT_Path,"");
        zkClient.createPersistent(PRODUCT_SOLDOUT_Path+"/"+product.getId(),"false");
      }


      IZkDataListener listener = new IZkDataListener(){

        @Override
        public void handleDataChange(String dataPath,Object data) throws Exception{
          log.error("zookeeper监听到数据变动："+data);
          productSoldOutMap.put(product.getId(), (Boolean) data);
        }

        @Override
        public void handleDataDeleted(String dataPath) throws Exception{

        }
      };
      zkClient.subscribeDataChanges(PRODUCT_SOLDOUT_Path+"/"+product.getId(),listener);
    }
  }


  @GetMapping(value = "/deductStock/{productId}")
  @ApiOperation(value = "deduct by id")
  public Object deductStock(@PathVariable(value = "productId") @NonNull Integer productId){
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("status",200);
    if(productSoldOutMap.get(productId) ==true){// 如果内存里已经有标记，连 redis 都不用访问 直接返回，用内存阻挡绝大部分访问
      zkClient.writeData(PRODUCT_SOLDOUT_Path+"/"+productId,"true"); // 通知其他节点修改内存里的productSoldOutMap为 true
      map.put("message","已卖完");
      map.put("stock",0);
      return map;
    }
    Long stock = redisUtils.decr(PRODUCT_STOCK + "_" + productId,1);
    if(stock == 0){
      map.put("message","商品没有准备好");
    }
    if(stock < 0){
      redisUtils.incr(PRODUCT_STOCK + "_" + productId,1); //如果库存小于 0，则把减掉的库存加上去
      productSoldOutMap.put(productId, true); // 修改当前节点内存里的 map
      zkClient.writeData(PRODUCT_SOLDOUT_Path+"/"+productId,"true"); // 通知其他节点修改内存里的productSoldOutMap为 true
      map.put("message","已卖完");
      map.put("stock",0);

    }
    else{
      map.put("message","扣减成功");
      map.put("stock",stock);
      try{
        createOrder("wang",1);
      }catch (Exception e){
        e.printStackTrace();
        map.put("message","出错啦");
        stock = redisUtils.incr(PRODUCT_STOCK + "_" + productId,1);
        zkClient.writeData(PRODUCT_SOLDOUT_Path+"/"+productId,"false"); // 修改 path 节点里的值
        productSoldOutMap.remove(productId);
        map.put("stock",stock);
      }
    }
    return map;
  }


  public void createOrder(String userId,Integer productId) throws Exception{ // 插入订单环节很容易出错，所有出错的地方，产品 stock+1 售完标记恢复 false

    if(userId.isEmpty()){ throw new Exception("用户 id 为空"); }
    Order order = Order.builder().productId(productId).userId(userId).build();
    //kafkaTemplate.send("topic2",order);

  }
}

