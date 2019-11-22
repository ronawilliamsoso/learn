package com.example.learn.configFromYml;

import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Data
@NoArgsConstructor
@ConfigurationProperties(prefix= "ymlbean")
public class YmlBeanPerson{


  private String name;

  private Integer age;

  private Date birthday;

  List<String>  pets;

  List<String>  cars;

  Map<String,Object> maps;

  YmlGirlfriend girlfriend;

}
