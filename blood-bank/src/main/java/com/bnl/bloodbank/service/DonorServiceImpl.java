package com.bnl.bloodbank.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bnl.bloodbank.utility.UserRequestsResponse;
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
        donor.setPassword(passwordEncoder.encode(donor.getPassword()));
        donorRepository.save(donor);
        // donorFromRepo.setPassword(passwordEncoder.encode(donor.getPassword()));
        // donorFromRepo.setEmail(donor.getEmail());
        // donorFromRepo.setAddress(donor.getAddress());
        // donorFromRepo.setCity(donor.getCity());
        // donorFromRepo.setState(donor.getState());
        // donorFromRepo.setDateOfBirth(donor.getDateOfBirth());
        // donorFromRepo.setGender(donor.getGender());
        // donorFromRepo.setPhoneNumber(donor.getPhoneNumber());
        
        return "Successfully updated";
    }

    @Override
    public String addRequest(String username, Request request) throws UsernameNotFoundException {
        Donor donorFromRepo = findByUsername(username);
        if(request.getStatus() == null){
            request.setStatus("pending");
        }
        if(request.getDate() == null){
            request.setDate(LocalDate.now());
        }
        request.setDonor(donorFromRepo);
        List<Request> requests = donorFromRepo.getRequests();
        requests.add(request);
        donorFromRepo.setRequests(requests);
        return "Request Successfully added to : " + username;
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
    public List<Request> getRequests(String username) throws UsernameNotFoundException {
        if(!isUsernamePresent(username)) throw new UsernameNotFoundException("username " + username + " not found");
        return donorRepository.findRequestsByUsername(username);
    }
    
    
    @Override
    public List<Request> getPendingRequests(String username) throws UsernameNotFoundException {
        if(!isUsernamePresent(username)) throw new UsernameNotFoundException("username " + username + " not found");
        return donorRepository.findPendingRequestsByUsername(username);
    }

    @Override
    public List<UserRequestsResponse> getUserAndRequestDetails() {
        List<Donor> response = donorRepository.getUserAndRequestDetails();
        List<UserRequestsResponse> ret = new ArrayList<>();
        response.stream().forEach(d -> {
            d.getRequests().stream()
                    .filter(req -> req.getStatus().equalsIgnoreCase("pending"))
                    .forEach(dr-> {
                        UserRequestsResponse r = UserRequestsResponse.builder()
                                .username(d.getUsername())
                                .email(d.getEmail())
                                .state(d.getState())
                                .city(d.getCity())
                                .address(d.getAddress())
                                .phoneNumber(d.getPhoneNumber())
                                .bloodGroup(dr.getBloodGroup())
                                .quantity(dr.getQuantity())
                                .date(dr.getDate())
                                .status(dr.getStatus())
                                .build();
                        ret.add(r);
                    });
        });
        return ret;
    }

    private boolean isUsernamePresent(String username){
        if(donorRepository.findByUsername(username).isEmpty()){
            return false;
        }
        return true;
    }

    private boolean isPhoneNumberPresent(long phoneNumber){
        if(donorRepository.findByPhoneNumber(phoneNumber).isEmpty()){
            return false;
        }
        return true;
    }
    
    
}
