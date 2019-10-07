package com.workday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workday.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
