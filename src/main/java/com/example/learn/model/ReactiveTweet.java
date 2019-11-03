package com.example.learn.model;

import java.util.Date;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Builder
@Document(collection = "tweets")
public class ReactiveTweet{

  @Id
  private Integer tweetId;

  @Size(max = 140)
  @NotBlank
  private String text;

  @NotNull
  private Date createDate = new Date();


}
