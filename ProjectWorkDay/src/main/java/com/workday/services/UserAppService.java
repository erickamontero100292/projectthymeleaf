package com.workday.services;


import com.workday.entity.EntityRol;
import com.workday.entity.EntityUserApp;
import com.workday.repository.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserAppService {


    @Autowired
    UserAppRepository repository;

    @Autowired
    RolService rolService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    public EntityUserApp save(EntityUserApp userApp) {
        userApp.setDateCreate(new Date());
        userApp.setPassword(passwordEncoder.encode(userApp.getPassword()));
        return repository.save(userApp);
    }

    public EntityUserApp save(EntityUserApp userApp, String rol) {
        userApp.setDateCreate(new Date());
        EntityRol entityRol = rolService.findByName(rol);
        userApp.setRol(entityRol);
        userApp.setPassword(passwordEncoder.encode(userApp.getPassword()));
        return repository.save(userApp);
    }


    public EntityUserApp update(EntityUserApp userApp) {
        userApp.setPassword(passwordEncoder.encode(userApp.getPassword()));
        return repository.save(userApp);
    }

    public EntityUserApp findById(long id) {
        return repository.findById(id).orElse(null);
    }

    public EntityUserApp findFirstByUser(String user) {
        return repository.findFirstByUser(user);
    }
}
