package com.example.demo.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
 
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
 

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "customer")
@DynamicInsert
@DynamicUpdate
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer customerId;
	
	@Column(length=20)
	private String firstName;
	
	@Column(length =2)
	private Integer age;
	
	@Column(nullable = false)
	private Boolean confirmed;
	
	@Column(nullable = false)
	private BigDecimal height;
	
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	
	

}
