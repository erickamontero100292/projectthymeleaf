package com.workday.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

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
	private Date fecha;
	
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public Registry(Long id, Date fecha, @NotNull Employee employee, @NotNull Long hours) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.employee = employee;
		this.hours = hours;
	}

	public Registry() {
		super();
	}
	
	
}
