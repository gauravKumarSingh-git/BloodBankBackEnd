package com.bnl.bloodbank.service;

import com.bnl.bloodbank.entity.BloodGroup;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;

public interface BloodGroupService {

    public String addBloodGroup(BloodGroup bloodGroup) throws AlreadyPresentException;

    public BloodGroup getBloodGroup(long bloodGroupId) throws NotPresentException;

    public String deleteBloodGroup(long bloodGroupId) throws NotPresentException;

    public String updateQuantity(String bloodGroup, long quantity) throws NotPresentException;

    public BloodGroup getByBloodGroup(String bloodGroup) throws NotPresentException;
    
}
