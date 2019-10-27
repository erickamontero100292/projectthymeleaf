package com.workday.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "roluser")
public class EntityRol {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

  /*  @OneToMany(mappedBy = "prueba", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<EntityUserApp> userList = new ArrayList<>();*/

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

    public EntityRol() {
    }

    public EntityRol(String name) {
        this.name = name;
    }


}
