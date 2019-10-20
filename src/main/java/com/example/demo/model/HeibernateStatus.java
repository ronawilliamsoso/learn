package com.example.demo.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Setter
@Getter
@ToString
@Entity
@Table(name = "heibernate_user")
@Builder
public class HeibernateUser{

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "user_name")
  private String userName;

  @Column(length = 20)
  private String company;

  @Column(length = 20)
  private String department;

  @OneToOne(mappedBy = "heibernateUser")
  @Cascade(value = CascadeType.ALL)
  private HeibernateUserAddress heibernateUserAddress;


  public HeibernateUser(){}


  public HeibernateUser(String userName,String company,String department){
    this.userName = userName;
    this.company = company;
    this.department = department;
  }
}
