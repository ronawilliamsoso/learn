package com.example.learn.service;

import com.example.learn.model.MUser;
import com.example.learn.repository.MUserAddressRepository;
import com.example.learn.repository.MUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MUserService{

  @Autowired
  public MUserRepository mUserRepository;

  @Autowired
  MUserAddressRepository mUserAddressRepository;

  public MUser register(MUser mUser) throws Exception{

    if(mUser.getId() == 0){
      throw new Exception("no admin should be added");
    }

    if(!mUser.getName().isEmpty() && !mUserRepository.existsById(mUser.getId())){
      return mUserRepository.save(mUser);
    }
    else{
      throw new Exception("姓名为空或者存在");
    }

  }


  public void register_normal(MUser mData){

    if(!mData.getName().isEmpty() && !mUserRepository.existsById(mData.getId())){
      mUserRepository.save(mData);
    }
  }

  public MUser register_normal_with_return(MUser mData){

    if(!mData.getName().isEmpty() && !mUserRepository.existsById(mData.getId())){
     return mUserRepository.save(mData);
    }else {
      return null;
    }
  }


  public void saveOrUpdate(MUser mData){
    if(!mUserRepository.existsById(mData.getId())){
      mUserRepository.save(mData);
    }
    else{
      mUserRepository.deleteById(mData.getId());
      mUserRepository.save(mData);
    }
  }

//  public MUser findAndEnrichOneUser(Integer userId){
//    if(!mUserRepository.existsById(userId)){
//      return null;
//    }
//    else{
//
//      Optional<String> cityOptional =  mUserAddressRepository.forCity(userId);
//      MUser mUser =  mUserRepository.getOne(userId);
//      mUser.setCity(cityOptional.get());
//      return mUser;
//    }
//  }

}
