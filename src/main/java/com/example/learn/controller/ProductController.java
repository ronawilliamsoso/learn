package com.example.learn.controller;

import com.example.learn.model.Product;
import com.example.learn.repository.ProductRepository;
import com.example.learn.utils.RedisUtils;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: demo
 * @description: 秒杀商品防止超卖
 * @author: Wei.Wang
 * @create: 2019-11-11 16:52
 **/


@RestController
@RequestMapping("/product")
public class ProductController{

public  static final String PRODUCT_STOCK = "product_stock";

  @Autowired
  private ProductRepository productRepository;





  @GetMapping(value = "/deductStock/{productId}")
  @ApiOperation(value = "deduct by id")
  public Object deductStock(@PathVariable(value = "productId") @NonNull Integer id){

    Map<String,Object> map = new HashMap<String,Object>();
    map.put("status",200);
    map.put("message","已卖完");
    Optional<Product> optionalProduct = productRepository.findById(id);
     optionalProduct.ifPresent(product -> {
       if(product.getProductStock()>0){
         product.setProductStock(product.getProductStock()-1);
         productRepository.save(product);
         map.put("message","扣减成功");
       }
     });
    return map;
  }
}

