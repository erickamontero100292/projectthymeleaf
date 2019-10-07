package com.workday.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
public class Employee {
	@Id
	@GeneratedValue
	private Long id;

	@NotEmpty
	private String name;

	@NotNull
	@ManyToOne
	private Workday workday;

	public Employee() {
		super();
	}

	public Employee(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setNamee(String name) {
		this.name = name;
	}

	public Workday getWorkday() {
		return workday;
	}

	public void setWorkday(Workday workday) {
		this.workday = workday;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Employee(Long id, @NotEmpty String name, @NotNull Workday workday) {
		super();
		this.id = id;
		this.name = name;
		this.workday = workday;
	}
	
	

}
