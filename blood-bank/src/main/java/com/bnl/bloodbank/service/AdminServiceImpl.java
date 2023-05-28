package com.bnl.bloodbank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bnl.bloodbank.entity.Admin;
import com.bnl.bloodbank.exception.UsernameAlredyPresentException;
import com.bnl.bloodbank.repository.AdminRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public String registerAdmin(Admin admin) throws Exception{
        Optional<Admin> fromRepo = adminRepository.findByUsername(admin.getUsername());
        if(fromRepo.isPresent()){
            throw new UsernameAlredyPresentException("Admin with Username " + admin.getUsername() + " is already present");
        }
        String hashPwd = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(hashPwd);
        adminRepository.save(admin);
        return "Admin with username " + admin.getUsername() + " successfully created";
    }
    
}
