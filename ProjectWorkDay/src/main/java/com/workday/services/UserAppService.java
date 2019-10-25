package com.workday.services;


import com.workday.entitty.EntityUserApp;
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
    BCryptPasswordEncoder passwordEncoder;


    public EntityUserApp save(EntityUserApp userApp) {
        userApp.setDateCreate(new Date());
        userApp.setRol("USER");
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
