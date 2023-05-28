package com.bnl.bloodbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bnl.bloodbank.entity.BloodBank;

public interface BloodBankRepository extends JpaRepository<BloodBank, Long> {
    
}
