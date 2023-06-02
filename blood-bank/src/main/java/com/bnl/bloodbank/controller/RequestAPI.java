package com.bnl.bloodbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.service.RequestService;

@RestController
@RequestMapping("/request")
public class RequestAPI {
    
    @Autowired
    RequestService requestService;

    /**
     * Add request details
     * @param request
     * @return ResponseEntity<String>
     */
    @PostMapping("/addRequest")
    public ResponseEntity<String> addRequest(@RequestBody Request request){
        return new ResponseEntity<>(requestService.addRequest(request), HttpStatus.CREATED);
    }

    /**
     * Update request status
     * @param requestId
     * @param status
     * @return ResponseEntity<String>
     * @throws NotPresentException
     */
    @PatchMapping("/updateStatus/{requestId}/{status}")
    public ResponseEntity<String> udpateStatus(@PathVariable long requestId ,@PathVariable String status) throws NotPresentException{
        return new ResponseEntity<>(requestService.updateStatus(requestId, status), HttpStatus.OK);
    }

    /**
     * Delete request
     * @param requestId
     * @return ResponseEntity<String>
     * @throws NotPresentException
     */
    @DeleteMapping("/deleteRequest/{requestId}")
    public ResponseEntity<String> deleteRequest(@PathVariable long requestId) throws NotPresentException{
        return new ResponseEntity<>(requestService.deleteRequest(requestId), HttpStatus.OK);
    }
}
