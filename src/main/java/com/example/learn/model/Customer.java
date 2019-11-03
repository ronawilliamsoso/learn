package com.example.learn.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Table(name = "customer")
@DynamicInsert
@DynamicUpdate
@Entity
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

	@Column( insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date lastModified;



}
