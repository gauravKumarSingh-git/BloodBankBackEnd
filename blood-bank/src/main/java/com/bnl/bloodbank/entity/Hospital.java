package com.bnl.bloodbank.entity;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long hospitalId;
    private String name;
    private String username;
    private String password;
    private String state;
    private String city;
    private String address;
    private long mobileNumber;
    @OneToMany(mappedBy = "hospital" ,cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Request> requests;
}
