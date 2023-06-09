package com.bnl.bloodbank.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.service.DonorService;
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
public class DonorServiceImpl implements DonorService {

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
    public String updateDonor(Donor donor) throws NotPresentException, AlreadyPresentException {
        Donor donorFromRepo = findById(donor.getDonorId());
        if(isPhoneNumberPresent(donor.getPhoneNumber())){
            if(donorFromRepo.getPhoneNumber() != donor.getPhoneNumber()){
                throw new AlreadyPresentException("Phone Number already present");
            }
        }
        donor.setPassword(passwordEncoder.encode(donor.getPassword()));
        donorRepository.save(donor);
        
        return "Successfully updated";
    }

    @Override
    public String addRequest(long id, Request request) throws NotPresentException {
        Donor donorFromRepo = findById(id);
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
        return "Request Successfully added to donor with ID: " + id;
    }

    
    @Override
    public String deleteDonor(long id) throws NotPresentException {
        Donor donorFromRepo = findById(id);
        donorRepository.delete(donorFromRepo);
        return "Donor with ID: " + id  + " successfully deleted";
    }

    
    @Override
    public Donor findById(long id) throws NotPresentException{
        Optional<Donor> fromRepo = donorRepository.findById(id);
        return fromRepo.orElseThrow(() -> new NotPresentException("ID: " + id + " not found"));
    }
    
    @Override
    public List<Request> getRequests(long id) throws NotPresentException {
        if(!isDonorPresent(id)) throw new NotPresentException("Donor with ID: " + id + " not found");
        return donorRepository.findRequestsById(id);
    }
    
    
    @Override
    public List<Request> getPendingRequests(long id) throws NotPresentException {
        if(!isDonorPresent(id)) throw new NotPresentException("Donor with ID: " + id + " not found");
        return donorRepository.findPendingRequestsById(id);
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

    private boolean isDonorPresent(long id){
        return donorRepository.findById(id).isPresent();
    }

    private boolean isUsernamePresent(String username){
        return donorRepository.findByUsername(username).isPresent();
    }

    private boolean isPhoneNumberPresent(long phoneNumber){
        return donorRepository.findByPhoneNumber(phoneNumber).isPresent();
    }
    
    
}
