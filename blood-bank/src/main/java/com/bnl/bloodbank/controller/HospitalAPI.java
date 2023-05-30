package com.bnl.bloodbank.controller;

import java.util.List;

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

import com.bnl.bloodbank.entity.Hospital;
import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.service.HospitalService;

@RestController
@RequestMapping("/hospital")
public class HospitalAPI {
    
    @Autowired
    HospitalService hospitalService;

    @PostMapping("/registerHospital")
    public ResponseEntity<String> registerHospital(@RequestBody Hospital hospital) throws AlreadyPresentException{
        return new ResponseEntity<>(hospitalService.registerHospital(hospital), HttpStatus.CREATED);
    }

    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<Hospital> findByUsername(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<>(hospitalService.findByUsername(username), HttpStatus.OK);
    } 

    @PatchMapping("/updateHospital")
    public ResponseEntity<String> updateHospital(@RequestBody Hospital hospital) throws UsernameNotFoundException, AlreadyPresentException{
        return new ResponseEntity<>(hospitalService.updateHospital(hospital), HttpStatus.OK);
    } 

    @DeleteMapping("/deleteHospital/{username}")
    public ResponseEntity<String> deleteHospital(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<>(hospitalService.deleteHospital(username), HttpStatus.OK);
    }

    @PatchMapping("/addRequest/{username}")
    public ResponseEntity<String> addRequest(@PathVariable String username ,@RequestBody Request request) throws UsernameNotFoundException{
        return new ResponseEntity<>(hospitalService.addRequest(username, request), HttpStatus.OK);
    }

    @GetMapping("/getRequests/{username}")
    public ResponseEntity<List<Request>> getRequests(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<>(hospitalService.getRequests(username), HttpStatus.OK);
    }

    @GetMapping("/getPendingRequests/{username}")
    public ResponseEntity<List<Request>> getPendingRequests(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<>(hospitalService.getPendingRequests(username), HttpStatus.OK);
    }
}
