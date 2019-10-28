package com.example.demo.demo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.model.MUser;
import com.example.demo.repository.MUserAddressRepository;
import com.example.demo.repository.MUserRepository;
import com.example.demo.service.MUserService;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class BDDMockito{

  @Mock
  MUserRepository mUserRepository;

  @Mock
  MUserAddressRepository mUserAddressRepository;

  @InjectMocks
  MUserService mUserService ;


  @Test
  @DisplayName("原生 mockito 测试用例")
  public void compare_mockito() throws Exception{//原生 mockito 测试
    when(mUserRepository.existsById(44)).thenReturn(false);//先决条件： 不存在 44 这个用户
    MUser mData = MUser.builder().id(44).name("ceshi").build();
    mUserService.register(mData); // 动作发生
    verify(mUserRepository).save(mData); //验证输出

  }

  @Test
  @DisplayName("比较上面那个例子，逻辑更好懂:假设用户不存在，那么当 service 新增有个用户的时候，repository 应该执行了新增操作")
  public void compare_bddmockito() throws Exception{

    given(mUserRepository.existsById(44)).willReturn(false);// 似乎更好懂： 假设 44 不存在
    MUser mData = MUser.builder().id(44).name("ceshi").build();
    mUserService.register(mData);              //动作发生
    then(mUserRepository).should().save(mData);// 动作发生时，mUserRepository 应该执行了 save 操作

  }

  @Test
  @DisplayName(" 重复 Id： never()")
  public void bddmockito_never() {
    given(mUserRepository.existsById(44)).willReturn(true);
    MUser mData = MUser.builder().id(44).name("ceshi").build();
    mUserService.register_normal(mData);
    then(mUserRepository).should(never()).save(mData);
  }










  @Test
  @DisplayName("bddmockito Exception")
  public void bddmockito_throw(){
    given(mUserRepository.existsById(44)).willReturn(true);
    MUser mData = MUser.builder().id(44).name("ceshi").build();
    try{
      mUserService.register(mData);
      fail("Should throw exception");
    }catch (Exception e){
      e.printStackTrace();
    }
    then(mUserRepository).should(never()).save(mData);
  }

  @Test  (expected = Exception.class)
  @DisplayName("bddmockito Exception")

  public void bddmockito_willthrow() throws Exception{

    MUser mData =  MUser.builder().id(0).name(anyString()).build();
    //given( mUserService.register(mData) ).willThrow( new Exception("no admin should be added")) ;
    when(mUserService.register(mData) ).thenThrow(new Exception("no admin should be added"));

  }

  @Test
  @DisplayName("更加合理的赋值： anyString() anyInt()")
  public void bddmockito_any(){

    Integer tempId = anyInt();
    given(mUserRepository.existsById(tempId)).willReturn(true,false);
    MUser mData = MUser.builder().id(tempId).name(anyString()).build();
    mUserService.saveOrUpdate(mData);

    then(mUserRepository).should().deleteById(mData.getId());
    then(mUserRepository).should().save(mData);

  }

  @Test
  @DisplayName("加入 assert")
  public void bddmockito_plus_assert(){

    Integer tempId = anyInt();
    given(mUserRepository.existsById(tempId)).willReturn(false);
    MUser mData =  mUserService.findAndEnrichOneUser(tempId);
    assertEquals(null, mData);
  }


  @Test
  @DisplayName("当结果跟输入的参数密切相关的时候，需要用 answer：输入是一个没有 id 的用户，输出是一个有 id 的用户")
  public void bddmockito_willAnswer(){


    MUser inputMUser = MUser.builder().name("Wei").city("Düsseldorf").build();


    //定义返回结果
    Answer<MUser> mUserAnswer = new Answer<MUser>(){
      @Override
      public MUser answer(InvocationOnMock invocationOnMock) throws Throwable{
        MUser mUser = invocationOnMock.getArgument(0);
        mUser.setId(randomInteger());
        return mUser;
      }
    };


    //given
    given(mUserRepository.save(any(MUser.class))).willAnswer(  mUserAnswer);


    //when
    MUser actuallMUser = mUserService.register_normal_with_return(inputMUser);

    //then
    assertNotNull(actuallMUser.getId() );
    assertEquals(inputMUser.getName(), actuallMUser.getName() );
    assertEquals(inputMUser.getCity(), actuallMUser.getCity() );
  }

  private Integer randomInteger() {
    return ThreadLocalRandom.current().nextInt(1000);
  }



}
