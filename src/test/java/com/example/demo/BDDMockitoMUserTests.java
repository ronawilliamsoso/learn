package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.learn.repository.MUserRepository;
import com.example.learn.service.MUserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BDDMockitoMUserTests{

  @Mock
  MUserRepository mDataRepositoryMock;

  @InjectMocks
  MUserService mDataServiceInjectMock;

  @Spy
  List<String> spiedList = new ArrayList<String>();

  @Mock
  List<String> mockedList  = new ArrayList<String>();



  @Test
  public void listSizeTest() throws Exception{

    List mockList = Mockito.mock(ArrayList.class);
    when(mockList.size()).thenReturn(100);
    assertEquals(100, mockList.size());

  }

  @Test
  @DisplayName("vertify void methods")
  public void vertifyTest() throws Exception{
    mockedList.add("one");
    verify(mockedList,times(1)).add("one");//激活上一个动作
  }

  @Test
  @DisplayName("spy 是真执行，mock 是假执行")
  public void spyAndMockListTest() throws Exception{

    spiedList.add("one");
    mockedList.add("one");
    verify(mockedList).add("one");//激活上一个动作
    assertEquals(0, mockedList.size() );//其实还是假执行

    assertEquals(1, spiedList.size() );// spy 才是真执行


  }

  @Test
  @DisplayName("ArgumentCaptor 捕获输入的参数")
  public void argumentCaptorTest(){

    mockedList.add("one");
    mockedList.add("two");

    ArgumentCaptor argument = ArgumentCaptor.forClass(String.class);
    verify(mockedList,times(2)).add((String) argument.capture());
    assertArrayEquals(new Object[]{"one","two"},argument.getAllValues().toArray());
  }





}
