package com.example.demo;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class NewJavaVersionFetures{


  @Test
  public void newWayCreatingCollections(){
    Set<Integer> set = Set.of(1,2,3,4,5);
    List<Integer> list = List.of(1,2,3,4,5);
    Map<String,Integer> map = Map.of("A",1,"B",2,"C",3);

    System.out.println("set" + set);
    System.out.println("list" + list);
    System.out.println("map" + map);
  }

  @Test
  public void takeWhileAndDropWhile(){
    System.out.println("takewhile:返回《首次》满足条件之前的元素");
    Stream.of("a","b","c","","e","f").takeWhile(x -> !x.isEmpty()).forEach(y -> System.out.println(y));//a b c

    System.out.println("dropwhile, 返回《首次》满足条件后的元素");
    Stream.of("g","h","i","","k","l").dropWhile(x -> !x.isEmpty()).forEach(y -> System.out.println(y));// k l

  }

  //对所有的继承自 colseable 的资源方便的关闭资源
  @Test
  public void tryWithResource() throws IOException{
    String src = "/Desketp/src/book.txt";
    String dst = "/Desketp/src/copybook.txt";
    try (InputStream is = new FileInputStream(src); OutputStream os = new FileOutputStream(dst)){
      byte[] bt = new byte[1024];
      int n;
      while ((n = is.read(bt)) >= 0){
        os.write(bt,0,n);
      }
    }
  }

  @Test
  public void mapFlatMap(){

    // example 1： map 总是再套上一层 optional
    assertEquals(Optional.of("Wei"),Optional.of("Wei").flatMap(s -> Optional.of("Wei")));
    assertEquals(Optional.of(Optional.of("Wei")),Optional.of("Wei").map(s -> Optional.of("Wei")));

    //example 2： 两种不同的方式拉平一个嵌套 list
    List<List<String>> list = Arrays.asList(Arrays.asList("Wang","Wei"),Arrays.asList("loves","coding"));
    List<String> expected = List.of("Wang","Wei","loves","coding");
    List<String> flatMap = list.stream().flatMap(stringList -> stringList.stream())// 将子集合拉平之后合并
                               .collect(Collectors.toList());
    assertEquals(expected,flatMap);

    List<String> noflatMap = new ArrayList<String>();
    list.forEach(stringList -> {
      stringList.forEach(string -> {
        noflatMap.add(string);
      });
    });
    assertEquals(expected,noflatMap);

  }

  @Test
  public void optional(){

    // optional to stream
    List<String> list = List.of("Wang","Wei","loves","coding");
    Optional.of(list).stream().forEach(s -> System.out.println(s));

    // or() 相当于三目运算
    Optional<String> rightValue = Optional.of("rightValue");
    Optional<String> defaultValue = Optional.of("defaultValue");

    Optional<String> result = rightValue.or(() -> defaultValue);
    assertEquals("rightValue",result.get());

    //ifPresentOrElse 另外一种三目运算
    Optional<String> notEmpty = Optional.of("notEmpty");
    notEmpty.ifPresentOrElse(s -> System.out.println("不为空"),() -> System.out.println("为空"));

  }

  @Test
  public void transferTo() throws IOException{
    var classLoader = ClassLoader.getSystemClassLoader();
    var inputStream = classLoader.getResourceAsStream("read.txt");
    var javastack = File.createTempFile("write","txt");
    try (var outputStream = new FileOutputStream(javastack)){
      inputStream.transferTo(outputStream);
    }


  }


}
