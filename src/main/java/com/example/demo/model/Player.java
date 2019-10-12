package com.example.demo.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "player")
@ToString
@EqualsAndHashCode
@DynamicInsert
@DynamicUpdate
public class Player {

	@Id
	@GeneratedValue
	Integer id;

	@Column(length = 30)
	private String name;

	@Column(length = 3)
	private String team;

	@Column(length = 20)
	private String position;

	@Column(length = 3)
	private Integer height;

	@Column(length = 3)
	private Integer weight;

	private BigDecimal age;

}
