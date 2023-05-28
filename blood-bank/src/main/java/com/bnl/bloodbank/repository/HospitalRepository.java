package com.bnl.bloodbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bnl.bloodbank.entity.Hospital;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    
}
