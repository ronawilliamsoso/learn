package com.example.demo;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.example.learn.model.MongoUser;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MongoDbTests {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Test
  public void contextLoads() {

    MongoUser user = new MongoUser();
    user.setId(1);
    user.setName("Wei");
    user.setSex("male");
    mongoTemplate.insert(user);

    user = new MongoUser();
    user.setId(2);
    user.setName("Wang");
    user.setSex("female");
    mongoTemplate.insert(user);

    System.out.println("ok！");
  }

  @Test
  public void findAllUsers() {
    MongoUser user = new MongoUser();
    List<MongoUser> users = mongoTemplate.findAll(MongoUser.class);
    users.forEach(u -> {
      System.out.println(u.toString());
    });

  }

  @Test
  public void findById() {
    MongoUser user = mongoTemplate.findById(1, MongoUser.class);
    System.out.println(user.toString());
  }
  @Test
  public void delete() {
    MongoUser user = new MongoUser();
    user.setId(1);
    mongoTemplate.remove(user);
  }

  @Test
  public void update() {
    mongoTemplate.updateFirst(query(where("name").is("Wei")), Update.update("name", "Wei is a fool"), MongoUser.class);
  }

}
