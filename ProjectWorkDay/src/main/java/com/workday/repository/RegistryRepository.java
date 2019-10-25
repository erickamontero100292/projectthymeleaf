package com.workday.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.workday.entitty.Employee;
import com.workday.entitty.EntityRegistry;

import java.util.List;


public interface RegistryRepository extends JpaRepository<EntityRegistry, Long>  {
	
	List<EntityRegistry> findByEmployee(Employee employee);

	List<EntityRegistry> findByEmployee(Employee employee, Sort sort);

	List<EntityRegistry> findAllByOrderByDateRegistryAsc();


}
