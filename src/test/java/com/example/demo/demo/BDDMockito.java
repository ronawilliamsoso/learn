package com.example.demo.demo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.model.MUser;
import com.example.demo.repository.MUserRepository;
import com.example.demo.service.MUserService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BDDMockito{

  @Mock
  MUserRepository mDataRepository;

  @InjectMocks
  MUserService mDataService;


  @Test
  @DisplayName("原生 mockito 测试用例")
  public void compare_mockito() throws Exception{//原生 mockito 测试
    when(mDataRepository.existsById(44)).thenReturn(false);//先决条件： 不存在 44 这个用户
    MUser mData = MUser.builder().id(44).name("ceshi").build();
    mDataService.register(mData); // 动作发生
    verify(mDataRepository).save(mData); //验证输出

  }

  @Test
  @DisplayName("比较上面那个例子，逻辑更好懂:假设用户不存在，那么当 service 新增有个用户的时候，repository 应该执行了新增操作")
  public void compare_bddmockito() throws Exception{

    given(mDataRepository.existsById(44)).willReturn(false);// 似乎更好懂： 假设 44 不存在
    MUser mData = MUser.builder().id(44).name("ceshi").build();
    mDataService.register(mData);              //动作发生
    then(mDataRepository).should().save(mData);// 动作发生时，mDataRepository 应该执行了 save 操作

  }

  @Test
  @DisplayName(" 重复 Id： never()")
  public void bddmockito_never() {
    given(mDataRepository.existsById(44)).willReturn(true);
    MUser mData = MUser.builder().id(44).name("ceshi").build();
    mDataService.register_normal(mData);
    then(mDataRepository).should(never()).save(mData);
  }

  @Test
  @DisplayName("bddmockito Exception")
  public void bddmockito_throw(){
    given(mDataRepository.existsById(44)).willReturn(true);
    MUser mData = MUser.builder().id(44).name("ceshi").build();
    try{
      mDataService.register(mData);
      fail("Should throw exception");
    }catch (Exception e){
      e.printStackTrace();
    }
    then(mDataRepository).should(never()).save(mData);
  }

  @Test  (expected = Exception.class)
  @DisplayName("bddmockito Exception")

  public void bddmockito_willthrow() throws Exception{

    MUser mData =  MUser.builder().id(0).name(anyString()).build();
    //given( mDataService.register(mData) ).willThrow( new Exception("no admin should be added")) ;
    when(mDataService.register(mData) ).thenThrow(new Exception("no admin should be added"));

  }

  @Test
  @DisplayName("更加合理的赋值： anyString() anyInt()")
  public void bddmockito_any(){

    Integer tempId = anyInt();
    given(mDataRepository.existsById(tempId)).willReturn(true,false);
    MUser mData = MUser.builder().id(tempId).name(anyString()).build();
    mDataService.saveOrUpdate(mData);

    then(mDataRepository).should().deleteById(mData.getId());
    then(mDataRepository).should().save(mData);

  }

  @Test
  @DisplayName("加入 assert")
  public void bddmockito_plus_assert(){

    Integer tempId = anyInt();
    given(mDataRepository.existsById(tempId)).willReturn(false);
    MUser mData =  mDataService.findOneUser(tempId);
    assertEquals(null, mData);
  }


}
