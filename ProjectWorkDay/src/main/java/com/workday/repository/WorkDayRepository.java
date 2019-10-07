package com.workday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.workday.model.Workday;


public interface WorkDayRepository extends JpaRepository<Workday, Long> {

}
