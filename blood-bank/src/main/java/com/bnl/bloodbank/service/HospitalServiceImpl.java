package com.bnl.bloodbank.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bnl.bloodbank.entity.Hospital;
import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.repository.HospitalRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public String registerHospital(Hospital hospital) throws AlreadyPresentException {
        if(isUsernamePresent(hospital.getUsername())){
            throw new AlreadyPresentException("Username alredy present");
        }
        if(isPhoneNumberPresent(hospital.getMobileNumber())){
            throw new AlreadyPresentException("Phone number already present");
        }
        hospital.setPassword(passwordEncoder.encode(hospital.getPassword()));
        hospitalRepository.save(hospital);
        return "Hospital with username : " + hospital.getUsername() + " successfully registered";
    }

    @Override
    public Hospital findByUsername(String username) throws UsernameNotFoundException {
        Optional<Hospital> formRepo = hospitalRepository.findByUsername(username);
        return formRepo.orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }


    @Override
    public String updateHospital(Hospital hospital) throws UsernameNotFoundException, AlreadyPresentException {
        Hospital fromRepo = findByUsername(hospital.getUsername());
        if(isPhoneNumberPresent(hospital.getMobileNumber())){
            if(fromRepo.getMobileNumber() != hospital.getMobileNumber()){
                throw new AlreadyPresentException("Phone Number already present");
            }
        }

        fromRepo.setAddress(hospital.getAddress());
        fromRepo.setCity(hospital.getCity());
        fromRepo.setMobileNumber(hospital.getMobileNumber());
        fromRepo.setName(hospital.getName());
        fromRepo.setPassword(passwordEncoder.encode(hospital.getPassword()));
        fromRepo.setState(hospital.getState());

        return "Successfully Updated hospital details with username : " + hospital.getUsername();
    }


    @Override
    public String deleteHospital(String username) throws UsernameNotFoundException {
        Hospital fromRepo = findByUsername(username);
        hospitalRepository.delete(fromRepo);
        return "Hospital with username: " + username + " successfully deleted";
    }

    
    @Override
    public String addRequest(String username, Request request) throws UsernameNotFoundException {
        Hospital fromRepo = findByUsername(username);
        if(request.getStatus() == null){
            request.setStatus("pending");
        }
        if(request.getDate() == null){
            request.setDate(LocalDate.now());
        }
        request.setHospital(fromRepo);
        List<Request> requests = fromRepo.getRequests();
        requests.add(request);
        fromRepo.setRequests(requests);
        return "Request succssfully added to : " + username;
    }

    @Override
    public List<Request> getRequests(String username) throws UsernameNotFoundException {
        if(!isUsernamePresent(username)) throw new UsernameNotFoundException("Username "+ username + " not found");
        return hospitalRepository.findRequestsByUsername(username);
    }

    @Override
    public List<Request> getPendingRequests(String username) throws UsernameNotFoundException {
        if(!isUsernamePresent(username)) throw new UsernameNotFoundException("Username "+ username + " not found");
        return hospitalRepository.findPendingRequestByUsername(username);
    }
    
    private boolean isUsernamePresent(String username){
        if(hospitalRepository.findByUsername(username).isEmpty()){
            return false;
        }
        return true;
    }

    private boolean isPhoneNumberPresent(long phoneNumber){
        if(hospitalRepository.findByMobileNumber(phoneNumber).isEmpty()){
            return false;
        }
        return true;
    }
}
