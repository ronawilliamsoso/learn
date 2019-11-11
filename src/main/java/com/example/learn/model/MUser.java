package com.example.learn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@Entity
@Table(name = "m_user")
public class MUser{

  @Id
  private Integer id;

  @Column(length = 20)
  @NonNull
  private String name;

  @Column(length =20)
  private String city;

  public MUser copyWithId(final Integer id) {
    return new MUser(id, this.name,this.city);
  }


}
