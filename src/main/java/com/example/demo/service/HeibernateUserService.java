package com.example.demo.service;

import com.example.demo.model.HeibernateUser;
import com.example.demo.repository.HeibernateUserRepository;
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
