package com.bnl.bloodbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnl.bloodbank.entity.Admin;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminAPI {

    @Autowired
    AdminService adminService;
    
    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody Admin admin) throws Exception{
        return new ResponseEntity<>(adminService.registerAdmin(admin), HttpStatus.CREATED);
    }

    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<Admin> findByUsername(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<>(adminService.findByUsername(username), HttpStatus.OK);
    }

    @PatchMapping("/updatePassword/{username}/{password}")
    public ResponseEntity<String> updatePassword(@PathVariable String username, @PathVariable String password) throws UsernameNotFoundException {
        return new ResponseEntity<>(adminService.updatePassword(username, password), HttpStatus.OK);
    }

    @DeleteMapping("/deleteAdmin/{username}")
    public ResponseEntity<String> deleteAdmin(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<>(adminService.deleteAdmin(username), HttpStatus.OK);
    }
}
