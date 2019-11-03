package com.example.learn.repository;

import com.example.learn.model.ReactiveTweet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactiveTweetRepository extends ReactiveMongoRepository<ReactiveTweet,Integer>{}
