package com.example.demo.repository;

import com.example.demo.model.ReactiveTweet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactiveTweetRepository extends ReactiveMongoRepository<ReactiveTweet,Integer>{}
