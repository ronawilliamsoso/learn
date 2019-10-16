package com.example.demo.controller;

import com.example.demo.model.ReactiveTweet;
import com.example.demo.repository.ReactiveTweetRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/reactiveTweet")
public class ReactiveTweetController{

  @Autowired
  private ReactiveTweetRepository reactiveTweetRepository;

  @GetMapping("/all")
  public Flux<ReactiveTweet> getAll(){
    return reactiveTweetRepository.findAll();
  }

  @PostMapping("/create")
  public Mono<ReactiveTweet> create(@Valid @RequestBody ReactiveTweet reactiveTweet){
    return reactiveTweetRepository.insert(reactiveTweet);
  }

  @GetMapping("/find/{id}")
  public Mono<ResponseEntity<ReactiveTweet>> getById(@PathVariable(value = "id") Integer tweetId){
    return reactiveTweetRepository.findById(tweetId).map(reactiveTweet -> ResponseEntity.ok(reactiveTweet))
                                  .defaultIfEmpty(ResponseEntity.notFound().build());

  }

  @PutMapping("/update")
  public Mono<ResponseEntity<ReactiveTweet>> update(@Valid @RequestBody ReactiveTweet reactiveTweet){
    return reactiveTweetRepository.findById(reactiveTweet.getTweetId()).flatMap(existingTweet -> {
      existingTweet.setText(reactiveTweet.getText());
      return reactiveTweetRepository.save(existingTweet);
    }).map(updatedTweet -> new ResponseEntity<>(updatedTweet,HttpStatus.OK)).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/delete/{id}")
  public Mono<ResponseEntity<Void>> deleteTweetById(@PathVariable(value = "id") Integer tweetId){
    return reactiveTweetRepository.findById(tweetId).flatMap(
        existTweet -> reactiveTweetRepository.delete(existTweet).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                                  .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

  }

  @GetMapping(value = "/stream/tweets", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ReactiveTweet> streamAllTweets(){
    return reactiveTweetRepository.findAll();
  }
}



