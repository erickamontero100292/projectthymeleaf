package com.workday.services;

import com.workday.entity.EntityRol;
import com.workday.repository.RolRepository;
import org.springframework.stereotype.Service;

@Service
public class RolService {

	private RolRepository repository;

	public RolService(RolRepository repository) {

		this.repository = repository;
	}

	public EntityRol save(EntityRol entityRol) {

		return repository.save(entityRol);
	}

	public EntityRol findByName(String name){
		return repository.findByName(name);
	}

}
