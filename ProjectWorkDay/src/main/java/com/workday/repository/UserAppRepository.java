package com.workday.repository;

import com.workday.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UserAppRepository   extends JpaRepository<UserApp, Long> {


    UserApp findFirstByUser(String email);
}
