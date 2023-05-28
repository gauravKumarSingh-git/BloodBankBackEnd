package com.bnl.bloodbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bnl.bloodbank.entity.Donor;

public interface DonorRepository extends JpaRepository<Donor, Long>{
    
}
