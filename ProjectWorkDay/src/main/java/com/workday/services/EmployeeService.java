package com.workday.services;

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
	
	
}
