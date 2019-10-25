package com.workday.services;

import java.util.List;

import com.workday.entitty.EntityUserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.workday.entitty.EntityEmployee;
import com.workday.repository.EmployeeRepository;

@Service
public class EmployeeService {

	
	private EmployeeRepository employeeRepository;

	@Autowired
	private  UserAppService appService;
	
	public EmployeeService(EmployeeRepository employeeRepository) {

		this.employeeRepository = employeeRepository;
	}

	public EntityEmployee save (EntityEmployee employee){
		
		return employeeRepository.save(employee);
	}
	
	
	public List<EntityEmployee>findAll(){
		return employeeRepository.findAll();
		
	}
	
	public EntityEmployee findById(Long id) {
		
		return employeeRepository.findById(id).orElse(null);
	}
	
	
	public EntityEmployee delete(Long id) {
		EntityEmployee employee = findById(id);
		employeeRepository.delete(employee);
		return employee;
	}

	public EntityEmployee findByUser(EntityUserApp userApp){
		return employeeRepository.findByUser(userApp);

	}
	public EntityEmployee findByUser(String userName){
		EntityUserApp userApp = appService.findFirstByUser(userName);
		EntityEmployee employee = employeeRepository.findByUser(userApp);
		return employee;

	}
}
