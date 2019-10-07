package com.workday.services;

import org.springframework.stereotype.Service;

import com.workday.model.Workday;
import com.workday.repository.WorkDayRepository;


@Service
public class WorkDayService {
	
	private WorkDayRepository repository;
	
	
	public WorkDayService( WorkDayRepository repository) {
		
		this.repository=repository;
	}
	
	public Workday save (Workday workday) {
		
		return repository.save(workday);
	}
	
	

}
