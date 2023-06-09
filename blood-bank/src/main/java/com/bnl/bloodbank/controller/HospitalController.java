package com.bnl.bloodbank.controller;

import java.util.List;

import com.bnl.bloodbank.exception.NotPresentException;
import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Not;
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

import com.bnl.bloodbank.entity.Hospital;
import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.service.HospitalService;

@RestController
@RequestMapping("/hospital")
@Validated
public class HospitalController {
    
    @Autowired
    HospitalService hospitalService;

    /**
     * Register new Hospital
     * @param hospital
     * @return ResponseEntity<String>
     * @throws AlreadyPresentException
     */
    @PostMapping("/registerHospital")
    public ResponseEntity<String> registerHospital(@Valid @RequestBody Hospital hospital) throws AlreadyPresentException{
        return new ResponseEntity<>(hospitalService.registerHospital(hospital), HttpStatus.CREATED);
    }

    /**
     * Find Hospital details by id
     * @param id
     * @return ResponseEntity<Hospital>
     * @throws NotPresentException
     */
    @GetMapping("/findById/{id}")
    public ResponseEntity<Hospital> findById(@PathVariable long id) throws NotPresentException {
        return new ResponseEntity<>(hospitalService.findById(id), HttpStatus.OK);
    }

    /**
     * Update Hospital details
     * @param hospital
     * @return ResponseEntity<String>
     * @throws NotPresentException
     * @throws AlreadyPresentException
     */
    @PatchMapping("/updateHospital")
    public ResponseEntity<String> updateHospital(@Valid @RequestBody Hospital hospital) throws NotPresentException, AlreadyPresentException{
        return new ResponseEntity<>(hospitalService.updateHospital(hospital), HttpStatus.OK);
    }

    /**
     * Delete hospital details by id
     * @param id
     * @return ResponseEntity<String>
     * @throws NotPresentException
     */
    @DeleteMapping("/deleteHospital/{id}")
    public ResponseEntity<String> deleteHospital(@PathVariable long id) throws NotPresentException{
        return new ResponseEntity<>(hospitalService.deleteHospital(id), HttpStatus.OK);
    }

    /**
     * Add requests made by hospital
     * @param id
     * @param request
     * @return ResponseEntity<String>
     * @throws NotPresentException
     */
    @PatchMapping("/addRequest/{id}")
    public ResponseEntity<String> addRequest(@PathVariable long id ,@Valid @RequestBody Request request) throws NotPresentException{
        return new ResponseEntity<>(hospitalService.addRequest(id, request), HttpStatus.OK);
    }

    /**
     * Get requests made by Hospital by id
     * @param id
     * @return ResponseEntity<List<Request>>
     * @throws NotPresentException
     */
    @GetMapping("/getRequests/{id}")
    public ResponseEntity<List<Request>> getRequests(@PathVariable long id) throws NotPresentException{
        return new ResponseEntity<>(hospitalService.getRequests(id), HttpStatus.OK);
    }

    /**
     * Get pending requests made by hospital
     * @param id
     * @return ResponseEntity<List<Request>>
     * @throws NotPresentException
     */
    @GetMapping("/getPendingRequests/{id}")
    public ResponseEntity<List<Request>> getPendingRequests(@PathVariable long id) throws NotPresentException {
        return new ResponseEntity<>(hospitalService.getPendingRequests(id), HttpStatus.OK);
    }
}
