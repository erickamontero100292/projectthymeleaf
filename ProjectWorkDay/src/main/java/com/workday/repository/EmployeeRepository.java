package com.workday.repository;

import com.workday.entity.EntityUserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import com.workday.entity.EntityEmployee;

public interface EmployeeRepository extends JpaRepository<EntityEmployee, Long> {


    EntityEmployee findByUser(EntityUserApp userApp);
}
