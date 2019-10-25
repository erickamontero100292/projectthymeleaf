package com.workday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workday.entitty.EntityWorkday;


public interface WorkDayRepository extends JpaRepository<EntityWorkday, Long> {

}
