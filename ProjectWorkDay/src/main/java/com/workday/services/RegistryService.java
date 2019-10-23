package com.workday.services;

import java.util.List;

import com.workday.model.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.workday.model.Registry;
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

	public List<Registry> findAllByOrderByDateRegistryAsc(){
		return registryRepository.findAllByOrderByDateRegistryAsc();

	}
	
	public List<Registry>findByEmployee(Employee employee){
		return registryRepository.findByEmployee(employee);
		
	}

	public List<Registry>findByEmployeeByOrderByDateRegistryAsc(Employee employee){
		List<Registry> registries= registryRepository.findByEmployee(employee,Sort.by(Sort.Direction.ASC, "dateRegistry"));
		return registries;

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
