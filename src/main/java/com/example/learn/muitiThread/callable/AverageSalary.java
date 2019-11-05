package com.example.learn.muitiThread.callable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AverageSalary{
  private Integer number;
  private double averageSalary;
}
