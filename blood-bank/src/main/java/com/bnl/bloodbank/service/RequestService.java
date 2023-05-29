package com.bnl.bloodbank.service;

import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.NotPresentException;

public interface RequestService {

    String addRequest(Request request);

    String updateStatus(long requestId, String status) throws NotPresentException;

    String deleteRequest(long requestId) throws NotPresentException;
    
}
