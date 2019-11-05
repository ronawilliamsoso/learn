package com.example.learn.garbageCollect;

import lombok.SneakyThrows;

/**
 * @program: demo
 * @description: 通过命令查看 heap 里的东西，以及gc详情
 *jvm 启动参数 ： -XX:+PrintGCDetails 可以打印类似于：
 * garbage-first heap   total 2062336K, used 1801805K
 *
 * 命令行： jps 找到需要分析的那个 pid，然后
 *
 * jstat -gcutil pid 5000
 *
 *5000 是每 5 秒监控一次
 *
 *    S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT    CGC    CGCT     GCT
 *   0.00 100.00   0.00  94.85  79.18  65.84      1    0.002     0    0.000     2    0.002    0.004
 *   0.00 100.00   0.00  94.85  79.18  65.84      1    0.002     0    0.000     2    0.002    0.004
 *   0.00 100.00   0.00  94.85  79.18  65.84      1    0.002     0    0.000     2    0.002    0.004
 *   0.00 100.00   0.00  94.85  79.18  65.84      1    0.002     0    0.000     2    0.002    0.004
 *   0.00 100.00   0.00  94.85  79.18  65.84      1    0.002     0    0.000     2    0.002    0.004
 *   0.00 100.00   0.00  94.85  79.18  65.84      1    0.002     0    0.000     2    0.002    0.004
 *   0.00 100.00   0.00  94.85  79.18  65.84      1    0.002     0    0.000     2    0.002    0.004
 *   0.00 100.00   0.00  94.85  79.18  65.84      1    0.002     0    0.000     2    0.002    0.004
 *   0.00 100.00   0.00  94.85  79.18  65.84      1    0.002     0    0.000     2    0.002    0.004
 *
 * S0: 新生代中Survivor space 0区已使用空间的百分比
 * S1: 新生代中Survivor space 1区已使用空间的百分比
 * E: 新生代已使用空间的百分比
 * O: 老年代已使用空间的百分比
 * YGC: 从应用程序启动到当前，发生Yang GC 的次数
 * YGCT: 从应用程序启动到当前，Yang GC所用的时间【单位秒】
 * FGC: 从应用程序启动到当前，发生Full GC的次数
 * FGCT: 从应用程序启动到当前，Full GC所用的时间
 * GCT: 从应用程序启动到当前，用于垃圾回收的总时间【单位秒】
 *
 *
 * @author: Wei.Wang
 * @create: 2019-11-05 23:11
 **/

public class CheckHeap{

  @SneakyThrows
  public static void main(String[] args){
    byte [] allocation1  = new byte[600000*1024];
    byte [] allocation2  = new byte[600000*1024];
    byte [] allocation3  = new byte[600000*1024];

    Thread.sleep(Integer.MAX_VALUE);
  }
}

