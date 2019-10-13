package com.workday.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UserApp {

    @Id
    @GeneratedValue
    private long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    private String email;
    private String password;

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

    public UserApp(Date dateCreate, String email, String password) {
        this.dateCreate = dateCreate;
        this.email = email;
        this.password = password;
    }

    public UserApp() {
    }
}