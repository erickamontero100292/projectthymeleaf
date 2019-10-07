package com.workday.services;

import org.springframework.stereotype.Service;

import com.workday.model.Employee;
import com.workday.repository.EmployeeRepository;

@Service
public class EmployeeService {

	
	private EmployeeRepository employeeRepository;
	
<<<<<<< HEAD
	
	
	public EmployeeService(EmployeeRepository employeeRepository) {

		this.employeeRepository = employeeRepository;
	}



=======
>>>>>>> branch 'master' of https://github.com/erickamontero100292/projectthymeleaf.git
	public Employee save (Employee employee){
		
		return employeeRepository.save(employee);
	}
	
	
}
