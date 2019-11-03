package com.example.learn.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@Entity
@Table(name = "hibernate_status")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HibernateStatus{

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "user_name",length = 10)
  private String userName;



}
