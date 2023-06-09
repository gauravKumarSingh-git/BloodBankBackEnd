package com.bnl.bloodbank.service;

import java.util.List;

import com.bnl.bloodbank.entity.Donor;
import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.utility.UserRequestsResponse;

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
     * @throws NotPresentException
     * @throws AlreadyPresentException
     */
    public String updateDonor(Donor donor) throws NotPresentException, AlreadyPresentException;

    /**
     * To add a request to list of request of a donor. Default value of status is pending and
     * date is current date.
     * @param id
     * @param request
     * @return String
     * @throws NotPresentException
     */
    public String addRequest(long id, Request request) throws NotPresentException;

    /**
     * Delete a donor by id
     * @param id
     * @return String
     * @throws NotPresentException
     */
    public String deleteDonor(long id) throws NotPresentException;

    /**
     * find a donor by id
     * @param id
     * @return Donor
     * @throws NotPresentException
     */
    public Donor findById(long id) throws NotPresentException;

    /**
     * get all requests made by a donor by username
     * @param username
     * @return List<Request>
     * @throws NotPresentException
     */
    public List<Request> getRequests(long id) throws NotPresentException;

    /**
     * Get all pending requests made by a donor by id
     * @param id
     * @return List<Reqeusts>
     * @throws NotPresentException
     */
    public List<Request> getPendingRequests(long id) throws NotPresentException;

    /**
     * Get pending requests and user details
     * @return
     */
    List<UserRequestsResponse> getUserAndRequestDetails();
}
