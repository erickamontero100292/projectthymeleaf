package com.workday.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.workday.entity.EntityEmployee;
import com.workday.entity.EntityRegistry;

import java.util.List;


public interface RegistryRepository extends JpaRepository<EntityRegistry, Long>  {
	
	List<EntityRegistry> findByEmployee(EntityEmployee employee);

	List<EntityRegistry> findByEmployee(EntityEmployee employee, Sort sort);

	List<EntityRegistry> findAllByOrderByDateRegistryAsc();

	List<EntityRegistry> findEntityRegistryByEmployee_NameContains(String name);

	List<EntityRegistry> findEntityRegistryByEmployee_NameContainsIgnoreCase(String name);


	List<EntityRegistry> findByEmployeeContaining(EntityEmployee entityEmployee);


}
