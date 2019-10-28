package com.workday.repository;

import com.workday.entity.EntityRol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<EntityRol, Long> {

    EntityRol findByName(String name);
}
