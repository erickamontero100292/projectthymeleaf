package com.workday.services;


import com.workday.model.UserApp;
import com.workday.repository.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAppService {


    @Autowired
    UserAppRepository repository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    public UserApp add(UserApp u) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return repository.save(u);
    }

    public UserApp findById(long id) {
        return repository.findById(id).orElse(null);
    }

    public UserApp findByEmail(String email) {
        return repository.findFirstByEmail(email);
    }
}
