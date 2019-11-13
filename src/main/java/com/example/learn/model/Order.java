package com.example.learn.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Order{

  private Integer orderId;

  private Integer productId;

  private  String userId;

}
