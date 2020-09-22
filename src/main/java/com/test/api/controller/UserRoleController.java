package com.test.api.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.test.api.model.UserRole;
import com.test.api.repository.UserRoleRepository;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserRoleController {
    private UserRoleRepository userRoleRepository;

    UserRoleController(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @GetMapping("/userroles/{id}")
    public Optional<UserRole> getUserRole(@PathVariable("id") final Long id) {
        return userRoleRepository.findById(id);
    }

    @GetMapping("/userroles")
    public List<UserRole> all() {
        return userRoleRepository.findAll();
    }

    @GetMapping("/userroles/")
    public List<UserRole> getValidUnitId(@RequestParam("userid") int userid, @RequestParam("unitid") int unitid) {
        return userRoleRepository.finUserUserUnitId(userid, unitid);
    }

    public List<UserRole> getValidUserUnitDate(@RequestParam("userid") int userid, @RequestParam("unitid") int unitid,  @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        return userRoleRepository.finUserUnitIdDate(userid, unitid, date);
    }

    
    @RequestMapping(value="/userroles/unitid/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public @ResponseBody List<UserRole> getValidUnit(@PathVariable int id) {
        return userRoleRepository.finUserUnitId(id);
    }
 
    @DeleteMapping("/userroles/{id}")
    void deleteUser(@PathVariable("id") Long id) {
        userRoleRepository.deleteById(id);
    }

    @PostMapping("/userroles")
    UserRole newUserRole(@RequestBody UserRole newUserRole) {
        LocalDateTime date1 = newUserRole.getValidFrom();
        LocalDateTime date2 = newUserRole.getValidTo();
        if(newUserRole.getValidTo() != null && date2.isBefore(date1)){
            return null;
        }
        else{return userRoleRepository.save(newUserRole);} 
    }

    @PutMapping("/userroles/{id}")
    UserRole replaceUserRole(@RequestBody UserRole newUserRole, @PathVariable Long id) {
        LocalDateTime date1 = newUserRole.getValidFrom();
        LocalDateTime date2 = newUserRole.getValidTo() ;
        return userRoleRepository.findById(id).map(userRole -> {
            if((newUserRole.getValidTo() != null && date2.isBefore(date1))){
                userRole.setValidFrom(userRole.getValidFrom());
                return userRole;
            }
            else{ 
            userRole.setValidFrom(newUserRole.getValidFrom());
            userRole.setValidTo(newUserRole.getValidTo());
            userRole.setUserId(userRole.getUserId()); 
            userRole.setUnitId(userRole.getUnitId());
            userRole.setRoleId(userRole.getRoleId());
            return userRoleRepository.save(userRole);}
        }).orElseGet(() -> {
            newUserRole.setId(id);
            return userRoleRepository.save(newUserRole);
        });
    }

}
