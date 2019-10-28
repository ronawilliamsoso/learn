package com.example.demo.service;

import com.example.demo.model.MUser;
import com.example.demo.repository.MUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MUserService{

  @Autowired
  public MUserRepository mDataRepository;

  public MUser register(MUser mData) throws Exception{

    if(mData.getId()==0){
      throw new Exception("no admin should be added");
    }

    if(!mData.getName().isEmpty() && !mDataRepository.existsById(mData.getId())){
      return  mDataRepository.save(mData);
    }
    else{
      throw new Exception("姓名为空或者存在");
    }

  }







  public void register_normal(MUser mData){

    if(!mData.getName().isEmpty() && !mDataRepository.existsById(mData.getId())){
      mDataRepository.save(mData);
    }
  }


  public void saveOrUpdate(MUser mData){
    if(!mDataRepository.existsById(mData.getId())){
      mDataRepository.save(mData);
    }
    else{
      mDataRepository.deleteById(mData.getId());
      mDataRepository.save(mData);
    }
  }

  public MUser findOneUser(Integer userId){
    if(!mDataRepository.existsById(userId)){
      return null;
    }
    else{
      return mDataRepository.getOne(userId);
    }
  }

}
