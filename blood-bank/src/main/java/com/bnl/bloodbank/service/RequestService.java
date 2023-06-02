package com.bnl.bloodbank.service;

import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.NotPresentException;

public interface RequestService {

    /**
     * Add new Request
     * @param request
     * @return String
     */
    String addRequest(Request request);

    /**
     * Update Request status
     * @param requestId
     * @param status
     * @return String
     * @throws NotPresentException
     */
    String updateStatus(long requestId, String status) throws NotPresentException;

    /**
     * Delete Request by requestId
     * @param requestId
     * @return String
     * @throws NotPresentException
     */
    String deleteRequest(long requestId) throws NotPresentException;
    
}
