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

import com.bnl.bloodbank.entity.BloodBank;
import com.bnl.bloodbank.entity.BloodGroup;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.service.BloodBankService;

@RestController
@RequestMapping("/bloodbank")
public class BloodBankAPI {
    
    @Autowired
    BloodBankService bloodBankService;

    @PostMapping("/addBloodBank")
    public ResponseEntity<String> addBloodBank(@RequestBody BloodBank bloodBank) throws AlreadyPresentException{
        return new ResponseEntity<>(bloodBankService.addBloodBank(bloodBank), HttpStatus.CREATED);
    }

    @GetMapping("/getBloodBank/{bloodBankId}")
    public ResponseEntity<BloodBank> getBloodBank(@PathVariable long bloodBankId) throws NotPresentException{
        return new ResponseEntity<>(bloodBankService.getBloodBank(bloodBankId), HttpStatus.OK);
    }

    @GetMapping("/getBloodGroups/{bloodBankId}")
    public ResponseEntity<List<BloodGroup>> getBloodGroups(@PathVariable long bloodBankId) throws NotPresentException{
        return new ResponseEntity<>(bloodBankService.getBloodGroups(bloodBankId), HttpStatus.OK);
    }

    @PatchMapping("/updateBloodBank")
    public ResponseEntity<String> updateBloodBank(@RequestBody BloodBank bloodBank) throws NotPresentException, AlreadyPresentException{
        return new ResponseEntity<>(bloodBankService.updateBloodBank(bloodBank), HttpStatus.OK);
    }

    @PatchMapping("/addBloodGroup/{bloodBankId}")
    public ResponseEntity<String> addBloodGroup(@PathVariable long bloodBankId, @RequestBody BloodGroup bloodGroup) throws NotPresentException{
        return new ResponseEntity<>(bloodBankService.addBloodGroup(bloodBankId, bloodGroup), HttpStatus.OK);
    }

    @DeleteMapping("/deleteBloodBank/{bloodBankId}")
    public ResponseEntity<String> deleteBloodBank(@PathVariable long bloodBankId) throws NotPresentException{
        return new ResponseEntity<>(bloodBankService.deleteBloodBank(bloodBankId), HttpStatus.OK);
    }

}
