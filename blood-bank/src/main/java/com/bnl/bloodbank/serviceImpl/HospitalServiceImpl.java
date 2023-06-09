package com.bnl.bloodbank.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.service.HospitalService;
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
    public Hospital findById(long id) throws NotPresentException {
        Optional<Hospital> formRepo = hospitalRepository.findById(id);
        return formRepo.orElseThrow(() -> new NotPresentException("ID: " + id + " not found"));
    }


    @Override
    public String updateHospital(Hospital hospital) throws NotPresentException, AlreadyPresentException {
        Hospital fromRepo = findById(hospital.getHospitalId());
        if(isPhoneNumberPresent(hospital.getMobileNumber())){
            if(fromRepo.getMobileNumber() != hospital.getMobileNumber()){
                throw new AlreadyPresentException("Phone Number already present");
            }
        }
        fromRepo.setPassword(passwordEncoder.encode(hospital.getPassword()));
        hospitalRepository.save(hospital);
        return "Successfully Updated hospital details with ID: " + hospital.getHospitalId();
    }


    @Override
    public String deleteHospital(long id) throws NotPresentException {
        Hospital fromRepo = findById(id);
        hospitalRepository.delete(fromRepo);
        return "Hospital with ID: " + id + " successfully deleted";
    }

    
    @Override
    public String addRequest(long id, Request request) throws NotPresentException {
        Hospital fromRepo = findById(id);
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
        return "Request succssfully added to user with ID: " + id;
    }

    @Override
    public List<Request> getRequests(long id) throws NotPresentException {
        if(!isUserPresent(id)) throw new NotPresentException("User with ID: "+ id + " not found");
        return hospitalRepository.findRequests(id);
    }

    @Override
    public List<Request> getPendingRequests(long id) throws NotPresentException {
        if(!isUserPresent(id)) throw new NotPresentException("User with ID: "+ id + " not found");
        return hospitalRepository.findPendingRequest(id);
    }
    
    private boolean isUsernamePresent(String username){
        return hospitalRepository.findByUsername(username).isPresent();
    }

    private boolean isUserPresent(long id){
        return hospitalRepository.findById(id).isPresent();
    }

    private boolean isPhoneNumberPresent(long phoneNumber){
        return hospitalRepository.findByMobileNumber(phoneNumber).isPresent();
    }
}
