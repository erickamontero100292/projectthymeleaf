package com.workday.entity;

import javax.persistence.*;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table (name = "employee")
public class EntityEmployee {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    @NotNull(message = "{error.workday.null}")
    @ManyToOne
    private EntityWorkday workday;

    @ManyToOne
    private EntityUserApp user;

    public EntityEmployee(Long id, String name) {
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

    public EntityWorkday getWorkday() {
        return workday;
    }

    public EntityUserApp getUser() {
        return user;
    }

    public void setUser(EntityUserApp user) {
        this.user = user;
    }

    public void setWorkday(EntityWorkday workday) {
        this.workday = workday;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EntityEmployee(Long id, @NotEmpty String name, @NotNull EntityWorkday workday) {
        super();
        this.id = id;
        this.name = name;
        this.workday = workday;
    }

	public EntityEmployee() {
		super();
	}

    public EntityEmployee(@NotEmpty String name, @NotNull(message = "{error.workday.null}") EntityWorkday workday, EntityUserApp user) {
        this.name = name;
        this.workday = workday;
        this.user = user;
    }
}
