package com.example.demo.demo;

import com.example.demo.model.ReactiveTweet;
import com.example.demo.repository.ReactiveTweetRepository;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReactiveTweetTest{

  @Autowired
  ReactiveTweetRepository reactiveTweetRepository;


  @Test
  public void testAddTweet(){

    ReactiveTweet reactiveTweet = ReactiveTweet.builder().text("add a tweet").createDate(new Date()).build();

    RouterFunction function = RouterFunctions.route(RequestPredicates.GET("/reactiveTweet/create"),request -> ServerResponse.ok().build());

    WebTestClient.bindToRouterFunction(function).build().post().uri("http://localhost:8080/reactiveTweet/create")
                 .contentType(MediaType.APPLICATION_JSON_UTF8)
                 .accept(MediaType.APPLICATION_JSON_UTF8)
                 .body(Mono.just(reactiveTweet),ReactiveTweet.class)
                 .exchange().expectStatus().isOk().expectHeader()
                 .contentType(MediaType.APPLICATION_JSON_UTF8)
                 .expectBody().jsonPath("$.tweetId")
                 .isNotEmpty().jsonPath("$.text")
                 .isEqualTo("add a tweet");
  }
}
