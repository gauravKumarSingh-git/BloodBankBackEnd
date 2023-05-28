package com.bnl.bloodbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bnl.bloodbank.entity.BloodGroup;

public interface BloodGroupRepository extends JpaRepository<BloodGroup, Long> {
    
}
