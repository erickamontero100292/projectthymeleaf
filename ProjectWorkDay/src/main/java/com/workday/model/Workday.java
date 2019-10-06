package com.workday.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@NoArgsConstructor
@Entity
public class Workday {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private Long numberHour;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getNumberHour() {
		return numberHour;
	}

	public void setNumberHour(Long numberHour) {
		this.numberHour = numberHour;
	}

	public Workday(Long id, String name, Long numberHour) {
		super();
		this.id = id;
		this.name = name;
		this.numberHour = numberHour;
	}

	public Workday() {
		super();
	}
	
	
	
	

}
