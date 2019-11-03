package com.example.learn.service;

import com.example.learn.model.MUser;
import com.example.learn.repository.MUserAddressRepository;
import com.example.learn.repository.MUserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MUserService{

  @Autowired
  public MUserRepository mDataRepository;

  @Autowired
  MUserAddressRepository mUserAddressRepository;

  public MUser register(MUser mData) throws Exception{

    if(mData.getId() == 0){
      throw new Exception("no admin should be added");
    }

    if(!mData.getName().isEmpty() && !mDataRepository.existsById(mData.getId())){
      return mDataRepository.save(mData);
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

  public MUser register_normal_with_return(MUser mData){

    if(!mData.getName().isEmpty() && !mDataRepository.existsById(mData.getId())){
     return mDataRepository.save(mData);
    }else {
      return null;
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

  public MUser findAndEnrichOneUser(Integer userId){
    if(!mDataRepository.existsById(userId)){
      return null;
    }
    else{

      Optional<String> cityOptional =  mUserAddressRepository.forCity(userId);
      MUser mUser =  mDataRepository.getOne(userId);
      mUser.setCity(cityOptional.get());
      return mUser;
    }
  }

}
