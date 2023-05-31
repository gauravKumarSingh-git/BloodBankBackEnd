package com.bnl.bloodbank.service;

import java.util.List;

import com.bnl.bloodbank.entity.Hospital;
import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;

public interface HospitalService {

    /**
     * Register new Hospital. The username and password should not be present in database otherwise
     * AlradyPresentException will be thrown
     * @param hospital
     * @return String
     * @throws AlreadyPresentException
     */
    public String registerHospital(Hospital hospital) throws AlreadyPresentException;

    /**
     * Find Hospital by username
     * @param username
     * @return Hospital
     * @throws UsernameNotFoundException
     */
    public Hospital findByUsername(String username) throws UsernameNotFoundException;

    /**
     * Update Hospital details except hospitalId and username, password will be encrypted before
     * updating, Mobile number must not be registered already.
     * @param hospital
     * @return String
     * @throws UsernameNotFoundException
     * @throws AlreadyPresentException
     */
    public String updateHospital(Hospital hospital) throws UsernameNotFoundException, AlreadyPresentException;

    /**
     * Delete Hospital details by username. Username should be present in database
     * @param username
     * @return String
     * @throws UsernameNotFoundException
     */
    public String deleteHospital(String username) throws UsernameNotFoundException;

    /**
     * Add new request to list of requests by hospital by username
     * @param username
     * @param request
     * @return String
     * @throws UsernameNotFoundException
     */
    public String addRequest(String username, Request request) throws UsernameNotFoundException;

    /**
     * Get requests made by a hospital by username
     * @param username
     * @return List<Request>
     * @throws UsernameNotFoundException
     */
    public List<Request> getRequests(String username) throws UsernameNotFoundException;

    /**
     * Get requets by hospital where status is pending
     * @param username
     * @return List<Request>
     * @throws UsernameNotFoundException
     */
    public List<Request> getPendingRequests(String username) throws UsernameNotFoundException;
    
}
