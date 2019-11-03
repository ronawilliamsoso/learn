package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.learn.model.MyDictionary;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class InjectMocks{

  @Mock
  Map<String,String> wordMap;


  @org.mockito.InjectMocks
  public MyDictionary dic =  new MyDictionary();



  @Test
  public void whenUseInjectMocksAnnotation_thenCorrect() {

    Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");

    assertEquals("aMeaning", dic.getMeaning("aWord"));
  }


}
