package com.workday.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.workday.model.Employee;
import com.workday.model.Registry;

import java.util.List;


public interface RegistryRepository extends JpaRepository<Registry, Long>  {
	
	List<Registry> findByEmployee(Employee employee);

	List<Registry> findByEmployee(Employee employee, Sort sort);

	List<Registry> findAllByOrderByDateRegistryAsc();


}
