package com.bnl.bloodbank.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    private long mobileNumber;
    private LocalDate lastUpdated;
    @OneToMany(mappedBy = "bloodBank" ,cascade = CascadeType.MERGE, orphanRemoval = true)
    @JsonManagedReference
    private List<BloodGroup> bloodgroups;
}
