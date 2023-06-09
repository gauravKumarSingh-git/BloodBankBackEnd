package com.bnl.bloodbank.serviceImpl;

import java.util.Optional;

import com.bnl.bloodbank.service.AdminService;
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
        adminRepository.saveAndFlush(admin);
        return "Admin with username " + admin.getUsername() + " successfully created";
    }

    @Override
    public Admin findById(long id) throws UsernameNotFoundException {
        Optional<Admin> fromRepo = adminRepository.findById(id);
        return fromRepo.orElseThrow(()-> new UsernameNotFoundException("Username with ID: " + id + " not found"));
    }

    @Override
    public String updatePassword(long id, String password) throws UsernameNotFoundException {
        Optional<Admin> fromRepo = adminRepository.findById(id);
        Admin admin = fromRepo.orElseThrow(()-> new UsernameNotFoundException("Username with ID: " + id + " not found"));
        admin.setPassword(passwordEncoder.encode(password));
        return "Password Successfully Changed";
    }

    @Override
    public String deleteAdmin(long id) throws UsernameNotFoundException {
        Optional<Admin> fromRepo = adminRepository.findById(id);
        Admin admin = fromRepo.orElseThrow(()-> new UsernameNotFoundException("ID: " + id + " not found"));
        adminRepository.delete(admin);
        return "Successfully Deleted admin with ID : " + id;
    }
    
}
