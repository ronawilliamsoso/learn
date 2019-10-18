package com.example.demo.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;




@Entity
@Table(name = "player")
@Data
@DynamicInsert
@DynamicUpdate
@Builder
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(length = 30, nullable = false)
	private String name;

	@Column(length = 3)
	private String team;

	@Column(length = 20)
	private String position;

	@Column(length = 3)
	private Integer height;

	@Column(length = 3)
	private Integer weight;

	@Column(columnDefinition = "DECIMAL(6,2) DEFAULT 0.00")
	private BigDecimal age;

}
