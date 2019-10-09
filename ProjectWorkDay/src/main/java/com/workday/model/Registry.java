package com.workday.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@NoArgsConstructor
@Entity
public class Registry {
	@Id
	@GeneratedValue
	private Long id;
	
	@CreatedDate
	private LocalDateTime dateRegistry;
	
	@NotNull
	@ManyToOne
	private Employee employee;

	@NotNull
	private Long hours;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDateRegistry() {
		return dateRegistry;
	}

	public void setDateRegistry(LocalDateTime dateRegistry) {
		this.dateRegistry = dateRegistry;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Long getHours() {
		return hours;
	}

	public void setHours(Long hours) {
		this.hours = hours;
	}

	public Registry(Long id, LocalDateTime dateRegistry, @NotNull Employee employee, @NotNull Long hours) {
		super();
		this.id = id;
		this.dateRegistry = dateRegistry;
		this.employee = employee;
		this.hours = hours;
	}

	public Registry() {
		super();
	}
	
	
}
