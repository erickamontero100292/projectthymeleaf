package com.workday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.workday.model.Registry;



public interface RegistryRepository extends JpaRepository<Registry, Long>  {

}
