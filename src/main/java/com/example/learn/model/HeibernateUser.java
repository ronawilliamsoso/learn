package com.example.learn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "heibernate_user")
@Builder
public class HeibernateUser{

  @Id
  private String name;

  @Column(length = 20)
  private String company;

  @Column(length = 20)
  private String department;

  public HeibernateUser(){}


  public HeibernateUser(String name,String company,String department){
    this.name = name;
    this.company = company;
    this.department = department;
  }
}
