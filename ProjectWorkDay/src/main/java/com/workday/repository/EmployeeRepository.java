package com.workday.repository;

import com.workday.model.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import com.workday.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    Employee findByUser(UserApp userApp);
}
