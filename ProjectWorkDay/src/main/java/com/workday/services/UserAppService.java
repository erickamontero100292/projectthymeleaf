package com.workday.services;


import com.workday.entitty.UserApp;
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


    public UserApp save(UserApp userApp) {
        userApp.setDateCreate(new Date());
        userApp.setRol("USER");
        userApp.setPassword(passwordEncoder.encode(userApp.getPassword()));

        return repository.save(userApp);
    }

    public UserApp findById(long id) {
        return repository.findById(id).orElse(null);
    }

    public UserApp findFirstByUser(String user) {
        return repository.findFirstByUser(user);
    }
}
