package com.bnl.bloodbank.service;

import java.util.List;

import com.bnl.bloodbank.entity.Donor;
import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;

public interface DonorService {

    /**
     * To add a new donor. The donor should not have username or password already present in
     * database otherwise AlreadyPresentException will be thrown.
     * @param donor
     * @return String
     * @throws AlreadyPresentException
     */
    public String registerDonor(Donor donor) throws AlreadyPresentException;

    /**
     * To update details of donor except username and donorId. The updated phone number should not
     * be already registered
     * @param donor
     * @return String
     * @throws UsernameNotFoundException
     * @throws AlreadyPresentException
     */
    public String updateDonor(Donor donor) throws UsernameNotFoundException, AlreadyPresentException;

    /**
     * To add a request to list of request of a donor. Default value of status is pending and
     * date is current date.
     * @param username
     * @param request
     * @return String
     * @throws UsernameNotFoundException
     */
    public String addRequest(String username, Request request) throws UsernameNotFoundException;

    /**
     * Delete a donor by username
     * @param username
     * @return String
     * @throws UsernameNotFoundException
     */
    public String deleteDonor(String username) throws UsernameNotFoundException;

    /**
     * find a donor by username
     * @param username
     * @return Donor
     * @throws UsernameNotFoundException
     */
    public Donor findByUsername(String username) throws UsernameNotFoundException;

    /**
     * get all requests made by a donor by username
     * @param username
     * @return List<Request>
     * @throws UsernameNotFoundException
     */
    public List<Request> getRequests(String username) throws UsernameNotFoundException;

    /**
     * Get all pending requests made by a donor by username
     * @param username
     * @return List<Reqeusts>
     * @throws UsernameNotFoundException
     */
    public List<Request> getPendingRequests(String username) throws UsernameNotFoundException;
    
}
