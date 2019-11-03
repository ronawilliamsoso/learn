package com.example.learn.service;

import com.example.learn.model.HeibernateUser;
import com.example.learn.repository.HeibernateUserRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HeibernateUserService{

  @Autowired
  HeibernateUserRepository heibernateUserRepository;


  public HeibernateUser getById(String id){
    Optional<HeibernateUser> optional = heibernateUserRepository.findById(id);
    return optional.orElseGet(null);
  }

  public void saveHeibernateStatusUser(HeibernateUser user){
    heibernateUserRepository.save(user);
  }

  public void deleteHeibernateStatusUserById(String id){
    heibernateUserRepository.deleteById(id);
  }
}
