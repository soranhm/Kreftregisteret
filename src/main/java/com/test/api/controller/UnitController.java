package com.test.api.controller;

import java.util.List;

import com.test.api.model.Unit;
import com.test.api.repository.UnitRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UnitController {

    private UnitRepository unitRepository;

    UnitController(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @GetMapping("/units")
    List<Unit> all() {
        return unitRepository.findAll();
    }
}
