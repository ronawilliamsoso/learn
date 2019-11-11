package com.example.learn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "muser_address")
public class MUserAddress{
	

	@Id
	private Integer userId;
	
	@Column(length=20)
	private String streeName;
	
	@Column(length =2)
	private Integer zipCode;
	
	@Column(nullable = false)
	private String city;

}
