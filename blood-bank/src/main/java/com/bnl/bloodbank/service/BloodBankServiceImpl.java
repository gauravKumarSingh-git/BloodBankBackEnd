package com.bnl.bloodbank.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnl.bloodbank.entity.BloodBank;
import com.bnl.bloodbank.entity.BloodGroup;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.repository.BloodBankRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BloodBankServiceImpl implements BloodBankService {

    @Autowired
    BloodBankRepository bloodBankRepository;

    @Override
    public String addBloodBank(BloodBank bloodBank) throws AlreadyPresentException {
        if(isMobileNumberPresent(bloodBank.getMobileNumber())){
            throw new AlreadyPresentException("Mobile number already present");
        }
        bloodBank.setLastUpdated(LocalDate.now());
        bloodBankRepository.save(bloodBank);
        return "Blood Bank " + bloodBank.getName() + " successfully saved";
    }

    @Override
    public BloodBank getBloodBank(long bloodBankId) throws NotPresentException {
        Optional<BloodBank> fromRepo = bloodBankRepository.findById(bloodBankId);
        return fromRepo.orElseThrow(() -> new NotPresentException("Blood Bank with given ID not present"));
    }

    @Override
    public List<BloodGroup> getBloodGroups(long bloodBankId) throws NotPresentException {
        BloodBank bloodBank = getBloodBank(bloodBankId);
        return bloodBank.getBloodgroups();
    }

    @Override
    public String updateBloodBank(BloodBank bloodBank) throws NotPresentException, AlreadyPresentException {
        BloodBank fromRepo = getBloodBank(bloodBank.getBloodBankId());
        if(isMobileNumberPresent(bloodBank.getMobileNumber())){
            if(fromRepo.getMobileNumber() != bloodBank.getMobileNumber()){
                throw new AlreadyPresentException("Mobile Number already present");
            }
        }

        fromRepo.setLastUpdated(LocalDate.now());
        fromRepo.setName(bloodBank.getName());
        fromRepo.setState(bloodBank.getState());
        fromRepo.setCity(bloodBank.getCity());
        fromRepo.setAddress(bloodBank.getAddress());
        fromRepo.setMobileNumber(bloodBank.getMobileNumber());

        return "Blood Bank " + bloodBank.getName() + " successfully updated";
    }

    @Override
    public String addBloodGroup(long bloodBankId, BloodGroup bloodGroup) throws NotPresentException {
        BloodBank fromRepo = getBloodBank(bloodBankId);
        fromRepo.setLastUpdated(LocalDate.now());
        bloodGroup.setBloodBank(fromRepo);
        List<BloodGroup> bloodGroups = fromRepo.getBloodgroups();
        bloodGroups.add(bloodGroup);
        fromRepo.setBloodgroups(bloodGroups);
        return "Blood Group successfully added to blood bank : " + fromRepo.getName();
    }

    @Override
    public String deleteBloodBank(long bloodBankId) throws NotPresentException {
        BloodBank fromRepo = getBloodBank(bloodBankId);
        bloodBankRepository.delete(fromRepo);
        return "Blood bank with ID : " + bloodBankId + " successfully deleted";
    }

    private boolean isMobileNumberPresent(long mobileNumber){
        if(bloodBankRepository.findByMobileNumber(mobileNumber).isEmpty()){
            return false;
        }
        return true;
    }
    
}
