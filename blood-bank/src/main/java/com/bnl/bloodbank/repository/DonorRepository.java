package com.bnl.bloodbank.repository;

import java.util.List;
import java.util.Optional;

import com.bnl.bloodbank.utility.UserRequestsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bnl.bloodbank.entity.Donor;
import com.bnl.bloodbank.entity.Request;

public interface DonorRepository extends JpaRepository<Donor, Long>{

    Optional<Donor> findByUsername(String username);

    Optional<Donor> findByPhoneNumber(long phoneNumber);

    @Query("SELECT d.requests from Donor d where d.donorId = :id")
    List<Request> findRequestsById(long id);

    @Query("SELECT r from Donor d inner join Request r on d.donorId = r.donor.donorId where d.donorId = :id and r.status = 'pending'")
    List<Request> findPendingRequestsById(long id);

    @Query("SELECT d from Donor d inner join Request r on d.donorId = r.donor.donorId where r.status = 'pending'")
    List<Donor> getUserAndRequestDetails();
}
