package com.example.learn.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order{

  private Integer orderId;

  private Integer productId;

  private  String userId;

}
