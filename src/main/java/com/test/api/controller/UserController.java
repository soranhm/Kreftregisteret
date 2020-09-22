package com.test.api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.test.api.model.User;
import com.test.api.repository.UserRepository;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private UserRepository userRepository;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    List<User> all() {
        return userRepository.findAll();
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id).map(user -> {
            user.setName(newUser.getName());
            user.setVersion(newUser.getVersion());
            return userRepository.save(user);
        }).orElseGet(() -> {
            newUser.setId(id);
            return userRepository.save(newUser);
        });
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUser(@PathVariable("id") final Long id) {
        return userRepository.findById(id);
    }

    @GetMapping("/users/")
    public List<User> getValidUnitDate(@RequestParam("unitid") int unitid,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        return userRepository.request2(unitid, date);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable("id") int id) {
        if (userRepository.checkuserIduserRole(id).isEmpty()) {
            System.out.println("NULL");
        } else {
            userRepository.deleteById((long) id);
        }
    }
}
