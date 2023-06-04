package com.bnl.bloodbank.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
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

    @NotNull(message = "name should not be null")
    private String name;

    @NotNull(message = "Username cannot be null")
    @Pattern(regexp = "[a-zA-Z0-9]+( [A-Za-z0-9]+)*", message = "Username should contain only alphabets and numerical values")
    @Size(min=5, message = "Username should be at least 5 characters long")
    @Size(max = 15, message = "Username should be less than 16 characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    @Size(max = 60, message = "Password should be less than 60 characters")
    private String password;

    @NotNull(message = "State should not be null")
    private String state;

    @NotNull(message = "City should not be null")
    private String city;

    @NotNull(message = "Address should not be null")
    private String address;

    @NotNull(message = "Mobile number cannot be null")
    @Min(value = 1000000000L, message = "Mobile number should be of 10 digits")
    @Max(value = 9999999999L, message = "Mobile number should be of 10 digits")
    private long mobileNumber;

    @OneToMany(mappedBy = "hospital" ,cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Request> requests;
}
