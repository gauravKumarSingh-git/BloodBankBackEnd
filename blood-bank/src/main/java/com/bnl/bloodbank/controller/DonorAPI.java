package com.bnl.bloodbank.controller;

import java.util.List;

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
public class DonorAPI {

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
     * @throws UsernameNotFoundException
     * @throws AlreadyPresentException
     */
    @PutMapping("/updateDonor")
    public ResponseEntity<String> updateDonor(@Valid @RequestBody Donor donor) throws UsernameNotFoundException, AlreadyPresentException{
        return new ResponseEntity<>(donorService.updateDonor(donor), HttpStatus.OK);
    }

    /**
     * To add request of a donor by username
     * @param username
     * @param request
     * @return ResponseEntity<String>
     * @throws UsernameNotFoundException
     */
    @PatchMapping("/addRequest/{username}")
    public ResponseEntity<String> addRequest(@PathVariable String username,@Valid @RequestBody Request request) throws UsernameNotFoundException{
        return new ResponseEntity<>(donorService.addRequest(username, request), HttpStatus.OK);
    }

    /**
     * To delete donor by username
     * @param username
     * @return ResponseEntity<String>
     * @throws UsernameNotFoundException
     */
    @DeleteMapping("/deleteDonor/{username}")
    public ResponseEntity<String> deleteDonor(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<>(donorService.deleteDonor(username), HttpStatus.OK);
    }

    /**
     * To find a donor by username
     * @param username
     * @return ResponseEntity<Donor>
     * @throws UsernameNotFoundException
     */
    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<Donor> findByUsername(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<Donor>(donorService.findByUsername(username), HttpStatus.OK);
    }

    /**
     * to get Requests made by a donor by username
     * @param username
     * @return ResponseEntity<List<Request>>
     * @throws UsernameNotFoundException
     */
    @GetMapping("/getRequests/{username}")
    public ResponseEntity<List<Request>> getRequests(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<>(donorService.getRequests(username), HttpStatus.OK);
    }

    /**
     * To get pending requests of a donor
     * @param username
     * @return ResponseEntity<List<Request>>
     * @throws UsernameNotFoundException
     */
    @GetMapping("/getPendingRequests/{username}")
    public ResponseEntity<List<Request>> getPendingRequests(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<>(donorService.getPendingRequests(username), HttpStatus.OK);
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
