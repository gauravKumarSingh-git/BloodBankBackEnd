package com.bnl.bloodbank.service;

import java.util.List;

import com.bnl.bloodbank.entity.BloodBank;
import com.bnl.bloodbank.entity.BloodGroup;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;

public interface BloodBankService {

    public String addBloodBank(BloodBank bloodBank) throws AlreadyPresentException;

    public BloodBank getBloodBank(long bloodBankId) throws NotPresentException;

    public List<BloodGroup> getBloodGroups(long bloodBankId) throws NotPresentException;

    public String updateBloodBank(BloodBank bloodBank) throws NotPresentException, AlreadyPresentException;

    public String addBloodGroup(long bloodBankId, BloodGroup bloodGroup) throws NotPresentException;

    public String deleteBloodBank(long bloodBankId) throws NotPresentException;
    
}
