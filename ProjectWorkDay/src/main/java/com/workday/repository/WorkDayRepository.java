package com.workday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workday.entitty.Workday;


public interface WorkDayRepository extends JpaRepository<Workday, Long> {

}
