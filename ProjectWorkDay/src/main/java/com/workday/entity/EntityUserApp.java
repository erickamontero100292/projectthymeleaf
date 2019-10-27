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
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    private String user;
    private String password;

    @OneToOne
    @JoinColumn(name = "rol")
    private EntityRol rol;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
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


    public EntityUserApp(Date dateCreate, String user, String password) {
        this.dateCreate = dateCreate;
        this.user = user;
        this.password = password;
    }

    public EntityUserApp(Date dateCreate, String user, String password, String nameRol) {
        this.dateCreate = dateCreate;
        this.user = user;
        this.password = password;
    }

    public EntityUserApp() {
    }

    public List<EntityEmployee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EntityEmployee> employeeList) {
        this.employeeList = employeeList;
    }

    public EntityRol getRol() {
        return rol;
    }

    public void setRol(EntityRol rol) {
        this.rol = rol;
    }
}