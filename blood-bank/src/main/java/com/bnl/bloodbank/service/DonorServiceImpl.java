package com.bnl.bloodbank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bnl.bloodbank.entity.Donor;
import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.repository.DonorRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DonorServiceImpl implements DonorService{

    @Autowired
    DonorRepository donorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public String registerDonor(Donor donor) throws AlreadyPresentException {
        if(isUsernamePresent(donor.getUsername())){
            throw new AlreadyPresentException("Username alredy present");
        }
        if(isPhoneNumberPresent(donor.getPhoneNumber())){
            throw new AlreadyPresentException("Phone number already present");
        }
        donor.setPassword(passwordEncoder.encode(donor.getPassword()));
        donorRepository.save(donor);
        return "Donor with username : " + donor.getUsername() + " successfully registered";
    }

    @Override
    public String updateDonor(Donor donor) throws UsernameNotFoundException, AlreadyPresentException {
        Donor donorFromRepo = findByUsername(donor.getUsername());
        if(isPhoneNumberPresent(donor.getPhoneNumber())){
            if(donorFromRepo.getPhoneNumber() != donor.getPhoneNumber()){
                throw new AlreadyPresentException("Phone Number already present");
            }
        }
        donorFromRepo.setPassword(passwordEncoder.encode(donor.getPassword()));
        donorFromRepo.setEmail(donor.getEmail());
        donorFromRepo.setAddress(donor.getAddress());
        donorFromRepo.setCity(donor.getCity());
        donorFromRepo.setState(donor.getState());
        donorFromRepo.setDateOfBirth(donor.getDateOfBirth());
        donorFromRepo.setGender(donor.getGender());
        donorFromRepo.setPhoneNumber(donor.getPhoneNumber());
        return "Successfully updated";
    }

    @Override
    public String addRequest(String username, Request request) throws UsernameNotFoundException {
        Donor donorFromRepo = findByUsername(username);
        List<Request> requests = donorFromRepo.getRequests();
        requests.add(request);
        donorFromRepo.setRequests(requests);
        return "Request Successfully added";
    }

    
    @Override
    public String deleteDonor(String username) throws UsernameNotFoundException {
        Donor donorFromRepo = findByUsername(username);
        donorRepository.delete(donorFromRepo);
        return "Donor with username : " + username + " successfully deleted";
    }

    
    @Override
    public Donor findByUsername(String username) throws UsernameNotFoundException{
        Optional<Donor> fromRepo = donorRepository.findByUsername(username);
        return fromRepo.orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }
    
    @Override
    public List<Request> getRequestsOrderByDate(String username) throws UsernameNotFoundException {
        if(!isUsernamePresent(username)) throw new UsernameNotFoundException("username " + username + " not found");
        return donorRepository.findRequestsByUsernameOrderByDate(username);
    }
    
    private boolean isUsernamePresent(String username){
        if(donorRepository.findByUsername(username).isEmpty()){
            return false;
        }
        return true;
    }

    private boolean isPhoneNumberPresent(int phoneNumber){
        if(donorRepository.findByPhoneNumber(phoneNumber).isEmpty()){
            return false;
        }
        return true;
    }
    
    
    
}
