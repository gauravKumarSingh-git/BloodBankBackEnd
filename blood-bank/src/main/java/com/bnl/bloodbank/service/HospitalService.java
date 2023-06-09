package com.bnl.bloodbank.service;

import java.util.List;

import com.bnl.bloodbank.entity.Hospital;
import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;
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
     * Find Hospital by id
     * @param id
     * @return Hospital
     * @throws UsernameNotFoundException
     */
    public Hospital findById(long id) throws NotPresentException;

    /**
     * Update Hospital details except hospitalId and username, password will be encrypted before
     * updating, Mobile number must not be registered already.
     * @param hospital
     * @return String
     * @throws UsernameNotFoundException
     * @throws AlreadyPresentException
     */
    public String updateHospital(Hospital hospital) throws NotPresentException, AlreadyPresentException;

    /**
     * Delete Hospital details by id. Username should be present in database
     * @param id
     * @return String
     * @throws NotPresentException
     */
    public String deleteHospital(long id) throws NotPresentException;

    /**
     * Add new request to list of requests by hospital by id
     * @param id
     * @param request
     * @return String
     * @throws NotPresentException
     */
    public String addRequest(long id, Request request) throws NotPresentException;

    /**
     * Get requests made by a hospital by username
     * @param id
     * @return List<Request>
     * @throws NotPresentException
     */
    public List<Request> getRequests(long id) throws NotPresentException;

    /**
     * Get requets by hospital where status is pending
     * @param id
     * @return List<Request>
     * @throws NotPresentException
     */
    public List<Request> getPendingRequests(long id) throws NotPresentException;
    
}
