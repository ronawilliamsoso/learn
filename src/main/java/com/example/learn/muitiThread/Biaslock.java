package com.example.learn.muitiThread;

import java.util.Vector;

public class Biaslock {

    public static Vector<Integer> vector = new Vector<Integer>();

    /**
     * 偏向锁   ﻿对于单线程，一个线程获得了锁，那么它（大部分程序无锁竞争）再次获得锁的概率非常大，所以去掉锁申请这个过程可以提高执行效率。  30%
     * 开启偏向锁：-XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
     * 关闭偏向锁：-XX:-UseBiasedLocking
     * @param args
     */
    public static void main(String[] args){
        long begin = System.currentTimeMillis();
        int count = 0;
        int num = 0;
        while(count < 10000000){
            vector.add(num);
            num = num + 5;
            count++;
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }

    // 开启偏向锁比不开启块 30%

}
