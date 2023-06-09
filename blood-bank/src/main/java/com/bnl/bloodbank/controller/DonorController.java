package com.bnl.bloodbank.controller;

import java.util.List;

import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.utility.UserRequestsResponse;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnl.bloodbank.entity.Donor;
import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.service.DonorService;

@RestController
@RequestMapping("/donor")
@Validated
public class DonorController {

    @Autowired
    DonorService donorService;

    /**
     * To register a new donor
     * @param donor
     * @return ResponseEntity<String>
     * @throws AlreadyPresentException
     */
    @PostMapping("/registerDonor")
    public ResponseEntity<String> registerDonor(@Valid @RequestBody Donor donor) throws AlreadyPresentException {
        return new ResponseEntity<>(donorService.registerDonor(donor), HttpStatus.CREATED);
    }

    /**
     * To update donor details which are already present in database
     * @param donor
     * @return ResponseEntity<String>
     * @throws NotPresentException
     * @throws AlreadyPresentException
     */
    @PutMapping("/updateDonor")
    public ResponseEntity<String> updateDonor(@Valid @RequestBody Donor donor) throws NotPresentException, AlreadyPresentException{
        return new ResponseEntity<>(donorService.updateDonor(donor), HttpStatus.OK);
    }

    /**
     * To add request of a donor by id
     * @param id
     * @param request
     * @return ResponseEntity<String>
     * @throws NotPresentException
     */
    @PatchMapping("/addRequest/{id}")
    public ResponseEntity<String> addRequest(@PathVariable long id,@Valid @RequestBody Request request) throws NotPresentException{
        return new ResponseEntity<>(donorService.addRequest(id, request), HttpStatus.OK);
    }

    /**
     * To delete donor by id
     * @param id
     * @return ResponseEntity<String>
     * @throws NotPresentException
     */
    @DeleteMapping("/deleteDonor/{id}")
    public ResponseEntity<String> deleteDonor(@PathVariable long id) throws NotPresentException{
        return new ResponseEntity<>(donorService.deleteDonor(id), HttpStatus.OK);
    }

    /**
     * To find a donor by id
     * @param id
     * @return ResponseEntity<Donor>
     * @throws UsernameNotFoundException
     */
    @GetMapping("/findById/{id}")
    public ResponseEntity<Donor> findById(@PathVariable long id) throws NotPresentException {
        return new ResponseEntity<Donor>(donorService.findById(id), HttpStatus.OK);
    }

    /**
     * to get Requests made by a donor by id
     * @param id
     * @return ResponseEntity<List<Request>>
     * @throws NotPresentException
     */
    @GetMapping("/getRequests/{id}")
    public ResponseEntity<List<Request>> getRequests(@PathVariable long id) throws NotPresentException{
        return new ResponseEntity<>(donorService.getRequests(id), HttpStatus.OK);
    }

    /**
     * To get pending requests of a donor
     * @param id
     * @return ResponseEntity<List<Request>>
     * @throws NotPresentException
     */
    @GetMapping("/getPendingRequests/{id}")
    public ResponseEntity<List<Request>> getPendingRequests(@PathVariable long id) throws NotPresentException{
        return new ResponseEntity<>(donorService.getPendingRequests(id), HttpStatus.OK);
    }

    /**
     * To get all the pending requests and donor details
     * @return List<UserRequestsResponse>
     */
    @GetMapping("/getUserAndRequestDetails")
    public ResponseEntity<List<UserRequestsResponse>> getUserAndRequestDetails(){
        return new ResponseEntity<>(donorService.getUserAndRequestDetails(), HttpStatus.OK);
    }


}
