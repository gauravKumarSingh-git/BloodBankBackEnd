package com.bnl.bloodbank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bnl.bloodbank.entity.Admin;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
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
            throw new AlreadyPresentException("Admin with Username " + admin.getUsername() + " is already present");
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
        return "Admin with username " + admin.getUsername() + " successfully created";
    }

    @Override
    public Admin findByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> fromRepo = adminRepository.findByUsername(username);
        return fromRepo.orElseThrow(()-> new UsernameNotFoundException("Username " + username + " not found"));
    }

    @Override
    public String updatePassword(String username, String password) throws UsernameNotFoundException {
        Optional<Admin> fromRepo = adminRepository.findByUsername(username);
        Admin admin = fromRepo.orElseThrow(()-> new UsernameNotFoundException("Username " + username + " not found"));
        admin.setPassword(passwordEncoder.encode(password));
        return "Password Successfully Changed";
    }

    @Override
    public String deleteAdmin(String username) throws UsernameNotFoundException {
        Optional<Admin> fromRepo = adminRepository.findByUsername(username);
        Admin admin = fromRepo.orElseThrow(()-> new UsernameNotFoundException("Username " + username + " not found"));
        adminRepository.delete(admin);
        return "Successfully Deleted admin with username : " + username;
    }
    
}
