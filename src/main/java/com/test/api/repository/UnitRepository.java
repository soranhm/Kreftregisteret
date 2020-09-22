package com.test.api.repository;

import com.test.api.model.Unit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {
}
