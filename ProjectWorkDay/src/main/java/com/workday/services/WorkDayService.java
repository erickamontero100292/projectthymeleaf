package com.workday.services;

import org.springframework.stereotype.Service;

import com.workday.entitty.Workday;
import com.workday.repository.WorkDayRepository;

import java.util.List;

@Service
public class WorkDayService {

	private WorkDayRepository repository;

	public WorkDayService(WorkDayRepository repository) {

		this.repository = repository;
	}

	public Workday save(Workday workday) {

		return repository.save(workday);
	}

	public List<Workday> findAll() {
		return repository.findAll();
	}
	
	public Workday findById(Long id) {
		return repository.findById(id).orElse(null);
		
	}
	
	public Workday delete(Long id) {
		Workday workday = findById(id);
		repository.delete(workday);
		return workday;
	}

}
