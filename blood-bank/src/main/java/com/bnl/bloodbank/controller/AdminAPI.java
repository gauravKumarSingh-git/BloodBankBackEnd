package com.bnl.bloodbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnl.bloodbank.entity.Admin;
import com.bnl.bloodbank.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminAPI {

    @Autowired
    AdminService adminService;
    
    @PostMapping("/registeradmin")
    public ResponseEntity<String> registerAdmin(@RequestBody Admin admin) throws Exception{
        return new ResponseEntity<>(adminService.registerAdmin(admin), HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>("test successfull", HttpStatus.OK);
    }
}
