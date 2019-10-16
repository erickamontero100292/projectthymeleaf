package com.workday.services;

import java.util.List;

import com.workday.model.UserApp;
import org.springframework.stereotype.Service;

import com.workday.model.Employee;
import com.workday.repository.EmployeeRepository;

@Service
public class EmployeeService {

	
	private EmployeeRepository employeeRepository;
		
	
	public EmployeeService(EmployeeRepository employeeRepository) {

		this.employeeRepository = employeeRepository;
	}

	public Employee save (Employee employee){
		
		return employeeRepository.save(employee);
	}
	
	
	public List<Employee>findAll(){
		return employeeRepository.findAll();
		
	}
	
	public Employee findById(Long id) {
		
		return employeeRepository.findById(id).orElse(null);
	}
	
	
	public Employee delete(Long id) {
		Employee employee = findById(id);
		employeeRepository.delete(employee);
		return employee;
	}

	public Employee findByUser(UserApp userApp){
		return employeeRepository.findByUser(userApp);

	}
	
}
