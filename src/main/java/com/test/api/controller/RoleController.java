package com.test.api.controller;

import java.util.List;

import com.test.api.model.Role;
import com.test.api.repository.RoleRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class RoleController {

    private RoleRepository roleRepository;

    RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping("/roles")
    List<Role> all() {
        return roleRepository.findAll();
    }

}
