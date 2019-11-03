package com.example.learn.repository;


import com.example.learn.model.HeibernateUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeibernateUserRepository extends JpaRepository<HeibernateUser,String>{}
