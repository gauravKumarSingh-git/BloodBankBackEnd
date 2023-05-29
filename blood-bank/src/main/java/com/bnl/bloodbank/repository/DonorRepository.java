package com.bnl.bloodbank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bnl.bloodbank.entity.Donor;
import com.bnl.bloodbank.entity.Request;

public interface DonorRepository extends JpaRepository<Donor, Long>{

    Optional<Donor> findByUsername(String username);

    Optional<Donor> findByPhoneNumber(int phoneNumber);

    @Query("SELECT d.requests from Donor d where d.username = :username")
    List<Request> findRequestsByUsernameOrderByDate(String username);
    
}
