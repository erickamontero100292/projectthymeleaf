package com.workday.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


@Data
@Entity
@Table(name = "registry")
public class EntityRegistry {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateRegistry;

    @ManyToOne
    private EntityEmployee employee;

    @NotNull
    private Long hours;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateRegistry() {
        return dateRegistry;
    }

    public void setDateRegistry(Date dateRegistry) {
        this.dateRegistry = dateRegistry;
    }

    public EntityEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(EntityEmployee employee) {
        this.employee = employee;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public EntityRegistry(Long id, Date dateRegistry, @NotNull EntityEmployee employee, @NotNull Long hours) {
        super();
        this.id = id;
        this.dateRegistry = dateRegistry;
        this.employee = employee;
        this.hours = hours;
    }

    public EntityRegistry() {
        this.dateRegistry = new Date();
    }

    public EntityRegistry(@NotNull Date dateRegistry, EntityEmployee employee, @NotNull Long hours) {
        this.dateRegistry = dateRegistry;
        this.employee = employee;
        this.hours = hours;
    }
}
