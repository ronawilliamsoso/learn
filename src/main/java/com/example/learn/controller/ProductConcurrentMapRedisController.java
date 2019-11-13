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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: demo
 * @description: 秒杀商品防止超卖，当类被加载到容器之后将秒杀产品读取到 redis,  秒杀的时候，商品卖完的标记，直接放入内存 能阻挡绝大部分访问
 * redisUtils.decr   redisUtils.incr 是原子操作，单线程串行执行，所以线程安全
 * @author: Wei.Wang
 * @create: 2019-11-11 16:52
 **/


@RestController
@RequestMapping("/productRedisConcurrentMap")
public class ProductConcurrentMapRedisController{

  public static final String PRODUCT_STOCK = "product_stock_redis";


  private static ConcurrentHashMap<Integer,Boolean> productSoldOutMap = new ConcurrentHashMap<>();

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  RedisUtils redisUtils;


  /**
   * 当类被加载到容器之后就会执行的一个方法
   **/
  @PostConstruct
  public void init(){
    List<Product> products = productRepository.findAll();
    for (Product product : products){
      redisUtils.set(PRODUCT_STOCK + "_" + product.getId(),product.getProductStock());
    }
  }


  @GetMapping(value = "/deductStock/{productId}")
  @ApiOperation(value = "deduct by id")
  public Object deductStock(@PathVariable(value = "productId") @NonNull Integer productId){
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("status",200);
    if(productSoldOutMap.get(productId)!=null){// 如果内存里已经有标记，连 redis 都不用访问
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
      productSoldOutMap.put(productId, true);
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
        productSoldOutMap.remove(productId);
        map.put("stock",stock);
      }
    }
    return map;
  }


  public void createOrder(String userId,Integer productId) throws Exception{

    if(userId.isEmpty()){ throw new Exception("用户 id 为空"); }
    Order order = Order.builder().productId(productId).userId(userId).build();

  }
}

