package com.bnl.bloodbank.service;

import java.util.List;

import com.bnl.bloodbank.entity.Hospital;
import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;

public interface HospitalService {

    public String registerHospital(Hospital hospital) throws AlreadyPresentException;

    public Hospital findByUsername(String username) throws UsernameNotFoundException;

    public String updateHospital(Hospital hospital) throws UsernameNotFoundException, AlreadyPresentException;

    public String deleteHospital(String username) throws UsernameNotFoundException;

    public String addRequest(String username, Request request) throws UsernameNotFoundException;

    public List<Request> getRequests(String username) throws UsernameNotFoundException;

    public List<Request> getPendingRequests(String username) throws UsernameNotFoundException;
    
}
