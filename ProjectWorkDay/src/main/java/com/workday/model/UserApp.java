package com.workday.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "userapp")
public class UserApp {

    @Id
    @GeneratedValue
    private long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    private String email;
    private String password;
    private String rol;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public UserApp(Date dateCreate, String email, String password) {
        this.dateCreate = dateCreate;
        this.email = email;
        this.password = password;
    }

    public UserApp(Date dateCreate, String email, String password, String rol) {
        this.dateCreate = dateCreate;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public UserApp() {
    }
}