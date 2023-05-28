package com.bnl.bloodbank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BloodGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bloodGroupId;
    private String bloodGroup;
    private long quantity;
}
