package com.workday.repository;

import com.workday.entitty.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UserAppRepository   extends JpaRepository<UserApp, Long> {


    UserApp findFirstByUser(String email);
}
