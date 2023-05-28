package com.bnl.bloodbank.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BloodBank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bloodBankId;
    private String name;
    private String state;
    private String city;
    private String address;
    private int mobileNumber;
    private LocalDate lastUpdated;
    @OneToMany(cascade = CascadeType.MERGE)
    private List<BloodGroup> bloodgroups;
}
