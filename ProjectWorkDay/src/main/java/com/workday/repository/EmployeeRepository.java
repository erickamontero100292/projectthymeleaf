package com.workday.repository;

import com.workday.entitty.EntityUserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import com.workday.entitty.EntityEmployee;

public interface EmployeeRepository extends JpaRepository<EntityEmployee, Long> {


    EntityEmployee findByUser(EntityUserApp userApp);
}
