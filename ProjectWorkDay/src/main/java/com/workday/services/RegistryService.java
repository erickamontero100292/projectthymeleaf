package com.workday.services;

import java.util.List;

import com.workday.model.Employee;
import org.springframework.stereotype.Service;

import com.workday.model.Registry;
import com.workday.model.Workday;
import com.workday.repository.RegistryRepository;


@Service
public class RegistryService {

	
	private RegistryRepository registryRepository;

	public RegistryService(RegistryRepository registryRepository) {

		this.registryRepository = registryRepository;
	}
	
	public Registry save (Registry registry){
		
		return registryRepository.save(registry);
	}
	
	
	public List<Registry>findAll(){
		return registryRepository.findAll();
		
	}
	
	public List<Registry>findByEmployee(Employee employee){
		return registryRepository.findByEmployee(employee);
		
	}
	
	public Registry findById(Long id) {
		return registryRepository.findById(id).orElse(null);
		
	}
	public Registry delete (Long id) {
		Registry registry =  findById(id);
		registryRepository.delete(registry);
		return registry;
	}
}
