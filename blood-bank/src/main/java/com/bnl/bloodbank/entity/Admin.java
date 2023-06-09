package com.bnl.bloodbank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long adminId;

    @NotNull(message = "Username cannot be null")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Username should contain only alphabets and numerical values")
    @Size(min=5, message = "Username should be at least 5 characters long")
    @Size(max = 15, message = "Username should be less than 16 characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    @Size(max = 60, message = "Password should be less than 60 characters")
    private String password;
}
