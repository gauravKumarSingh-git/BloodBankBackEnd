package com.bnl.bloodbank.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class AdminController {

    @Autowired
    AdminService adminService;

    /**
     * To register new admin
     * @param admin
     * @return ResponseEntity<String>
     * @throws Exception
     */
    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody Admin admin) throws Exception{
        return new ResponseEntity<>(adminService.registerAdmin(admin), HttpStatus.CREATED);
    }

    /**
     * Get admin details by id
     * @param id
     * @return ResponseEntity<Admin>
     * @throws UsernameNotFoundException
     */
    @GetMapping("/findById/{id}")
    public ResponseEntity<Admin> findById(@PathVariable long id) throws UsernameNotFoundException{
        return new ResponseEntity<>(adminService.findById(id), HttpStatus.OK);
    }

    /**
     * Update password of admin by id
     * @param id
     * @param password
     * @return ResponseEntity<String>
     * @throws UsernameNotFoundException
     */
    @PatchMapping("/updatePassword/{id}/{password}")
    public ResponseEntity<String> updatePassword(@PathVariable long id, @PathVariable String password) throws UsernameNotFoundException {
        return new ResponseEntity<>(adminService.updatePassword(id, password), HttpStatus.OK);
    }

    /**
     * Delete admin by id
     * @param id
     * @return ResponseEntity<String>
     * @throws UsernameNotFoundException
     */
    @DeleteMapping("/deleteAdmin/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable long id) throws UsernameNotFoundException{
        return new ResponseEntity<>(adminService.deleteAdmin(id), HttpStatus.OK);
    }
}
