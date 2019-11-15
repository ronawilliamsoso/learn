package com.example.learn.garbageCollect;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: demo
 * @description: 使用大量空间的用户
 * @author: Wei.Wang
 * @create: 2019-11-15 19:16
 **/

@Data
@AllArgsConstructor
public class UserProfile{

  private Integer userId;

  private String userName;

  final byte[] description = new byte[1024*100];
}

