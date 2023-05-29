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
public class DonorAPI {

    @Autowired
    DonorService donorService;

    @PostMapping("/registerDonor")
    public ResponseEntity<String> registerDonor(@RequestBody Donor donor) throws AlreadyPresentException {
        return new ResponseEntity<>(donorService.registerDonor(donor), HttpStatus.CREATED);
    }
    
    @PutMapping("/updateDonor")
    public ResponseEntity<String> updateDonor(@RequestBody Donor donor) throws UsernameNotFoundException, AlreadyPresentException{
        return new ResponseEntity<>(donorService.updateDonor(donor), HttpStatus.OK);
    }

    @PatchMapping("/addRequest/{username}")
    public ResponseEntity<String> addRequest(@PathVariable String username, @RequestBody Request request) throws UsernameNotFoundException{
        return new ResponseEntity<>(donorService.addRequest(username, request), HttpStatus.OK);
    }

    @DeleteMapping("/deleteDonor/{username}")
    public ResponseEntity<String> deleteDonor(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<>(donorService.deleteDonor(username), HttpStatus.OK);
    }

    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<Donor> findByUsername(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<Donor>(donorService.findByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/getRequests/{username}")
    public ResponseEntity<List<Request>> getRequestsOrderByDate(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<>(donorService.getRequestsOrderByDate(username), HttpStatus.OK);
    }

    @GetMapping("/getPendingRequests/{username}")
    public ResponseEntity<List<Request>> getPendingRequests(@PathVariable String username) throws UsernameNotFoundException{
        return new ResponseEntity<>(donorService.getPendingRequests(username), HttpStatus.OK);
    }


}
