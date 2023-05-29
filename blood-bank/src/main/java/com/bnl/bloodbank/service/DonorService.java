package com.bnl.bloodbank.service;

import java.util.List;

import com.bnl.bloodbank.entity.Donor;
import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;

public interface DonorService {

    public String registerDonor(Donor donor) throws AlreadyPresentException;

    public String updateDonor(Donor donor) throws UsernameNotFoundException, AlreadyPresentException;

    public String addRequest(String username, Request request) throws UsernameNotFoundException;

    public String deleteDonor(String username) throws UsernameNotFoundException;

    public Donor findByUsername(String username) throws UsernameNotFoundException;

    public List<Request> getRequestsOrderByDate(String username) throws UsernameNotFoundException;
    
}
