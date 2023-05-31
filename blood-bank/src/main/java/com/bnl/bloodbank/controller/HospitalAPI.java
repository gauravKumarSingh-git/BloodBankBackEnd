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

    /**
     * Register new Hospital
     * @param hospital
     * @return ResponseEntity<String>
     * @throws AlreadyPresentException
     */
    @PostMapping("/registerHospital")
    public ResponseEntity<String> registerHospital(@RequestBody Hospital hospital) throws AlreadyPresentException{
        return new ResponseEntity<>(hospitalService.registerHospital(hospital), HttpStatus.CREATED);
    }

    /**
     * Find Hospital details by username
     * @param username
     * @return ResponseEntity<Hospital>
     * @throws UsernameNotFoundException
     */
    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<Hospital> findByUsername(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<>(hospitalService.findByUsername(username), HttpStatus.OK);
    }

    /**
     * Update Hospital details
     * @param hospital
     * @return ResponseEntity<String>
     * @throws UsernameNotFoundException
     * @throws AlreadyPresentException
     */
    @PatchMapping("/updateHospital")
    public ResponseEntity<String> updateHospital(@RequestBody Hospital hospital) throws UsernameNotFoundException, AlreadyPresentException{
        return new ResponseEntity<>(hospitalService.updateHospital(hospital), HttpStatus.OK);
    }

    /**
     * Delete hospital details by username
     * @param username
     * @return ResponseEntity<String>
     * @throws UsernameNotFoundException
     */
    @DeleteMapping("/deleteHospital/{username}")
    public ResponseEntity<String> deleteHospital(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<>(hospitalService.deleteHospital(username), HttpStatus.OK);
    }

    /**
     * Add requests made by hospital
     * @param username
     * @param request
     * @return ResponseEntity<String>
     * @throws UsernameNotFoundException
     */
    @PatchMapping("/addRequest/{username}")
    public ResponseEntity<String> addRequest(@PathVariable String username ,@RequestBody Request request) throws UsernameNotFoundException{
        return new ResponseEntity<>(hospitalService.addRequest(username, request), HttpStatus.OK);
    }

    /**
     * Get requests made by Hospital by username
     * @param username
     * @return ResponseEntity<List<Request>>
     * @throws UsernameNotFoundException
     */
    @GetMapping("/getRequests/{username}")
    public ResponseEntity<List<Request>> getRequests(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<>(hospitalService.getRequests(username), HttpStatus.OK);
    }

    /**
     * Get pending requests made by hospital
     * @param username
     * @return ResponseEntity<List<Request>>
     * @throws UsernameNotFoundException
     */
    @GetMapping("/getPendingRequests/{username}")
    public ResponseEntity<List<Request>> getPendingRequests(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<>(hospitalService.getPendingRequests(username), HttpStatus.OK);
    }
}
