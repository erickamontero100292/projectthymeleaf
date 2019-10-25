package com.workday.repository;

import com.workday.entitty.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import com.workday.entitty.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    Employee findByUser(UserApp userApp);
}
