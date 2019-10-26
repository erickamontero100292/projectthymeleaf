package com.workday.repository;

import com.workday.entity.EntityUserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UserAppRepository   extends JpaRepository<EntityUserApp, Long> {


    EntityUserApp findFirstByUser(String email);
}
