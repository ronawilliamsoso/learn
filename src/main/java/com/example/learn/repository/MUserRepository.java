package com.example.learn.repository;


import com.example.learn.model.MUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MUserRepository extends JpaRepository<MUser,Integer>{


}
