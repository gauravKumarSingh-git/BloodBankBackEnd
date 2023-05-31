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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Donor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long donorId;
    private String username;
    private String password;
    private String email;
    private String state;
    private String city;
    private String address;
    private LocalDate dateOfBirth;
    private String gender;
    private long phoneNumber;
    @OneToMany(mappedBy = "donor" ,cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "donorReference")
    private List<Request> requests;
}
