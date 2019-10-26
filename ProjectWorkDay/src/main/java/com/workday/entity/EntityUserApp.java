package com.workday.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "userapp")
public class EntityUserApp {

    @Id
    @GeneratedValue
    private long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    private String user;
    private String password;
    private String rol;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntityEmployee> employeeList = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public EntityUserApp(Date dateCreate, String user, String password) {
        this.dateCreate = dateCreate;
        this.user = user;
        this.password = password;
    }

    public EntityUserApp(Date dateCreate, String user, String password, String rol) {
        this.dateCreate = dateCreate;
        this.user = user;
        this.password = password;
        this.rol = rol;
    }

    public EntityUserApp() {
    }

    public List<EntityEmployee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EntityEmployee> employeeList) {
        this.employeeList = employeeList;
    }
}