package com.workday.services;

import org.springframework.stereotype.Service;

import com.workday.entity.EntityWorkday;
import com.workday.repository.WorkDayRepository;

import java.util.List;

@Service
public class WorkDayService {

	private WorkDayRepository repository;

	public WorkDayService(WorkDayRepository repository) {

		this.repository = repository;
	}

	public EntityWorkday save(EntityWorkday workday) {

		return repository.save(workday);
	}

	public List<EntityWorkday> findAll() {
		return repository.findAll();
	}
	
	public EntityWorkday findById(Long id) {
		return repository.findById(id).orElse(null);
		
	}
	
	public EntityWorkday delete(Long id) {
		EntityWorkday workday = findById(id);
		repository.delete(workday);
		return workday;
	}

}
