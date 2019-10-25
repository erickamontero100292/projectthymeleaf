package com.workday.services;

import java.util.List;

import com.workday.entitty.EntityEmployee;
import com.workday.entitty.EntityRegistry;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.workday.repository.RegistryRepository;


@Service
public class RegistryService {

	
	private RegistryRepository registryRepository;

	public RegistryService(RegistryRepository registryRepository) {

		this.registryRepository = registryRepository;
	}
	
	public EntityRegistry save (EntityRegistry registry){
		
		return registryRepository.save(registry);
	}
	
	
	public List<EntityRegistry>findAll(){
		return registryRepository.findAll();
		
	}

	public List<EntityRegistry> findAllByOrderByDateRegistryAsc(){
		return registryRepository.findAllByOrderByDateRegistryAsc();

	}
	
	public List<EntityRegistry>findByEmployee(EntityEmployee employee){
		return registryRepository.findByEmployee(employee);
		
	}

	public List<EntityRegistry>findByEmployeeByOrderByDateRegistryAsc(EntityEmployee employee){
		List<EntityRegistry> registries= registryRepository.findByEmployee(employee,Sort.by(Sort.Direction.ASC, "dateRegistry"));
		return registries;

	}
	
	public EntityRegistry findById(Long id) {
		return registryRepository.findById(id).orElse(null);
		
	}
	public EntityRegistry delete (Long id) {
		EntityRegistry registry =  findById(id);
		registryRepository.delete(registry);
		return registry;
	}
}
