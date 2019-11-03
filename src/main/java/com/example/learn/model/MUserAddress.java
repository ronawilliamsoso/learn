package com.example.learn.model;

import javax.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MUserAddress{
	

	private Integer userId;
	
	@Column(length=20)
	private String streeName;
	
	@Column(length =2)
	private Integer zipCode;
	
	@Column(nullable = false)
	private String city;

}
