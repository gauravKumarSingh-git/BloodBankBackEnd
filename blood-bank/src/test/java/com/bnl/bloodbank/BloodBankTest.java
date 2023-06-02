package com.bnl.bloodbank;

import com.bnl.bloodbank.controller.BloodBankAPI;
import com.bnl.bloodbank.entity.BloodBank;
import com.bnl.bloodbank.entity.BloodGroup;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.repository.BloodBankRepository;
import com.bnl.bloodbank.service.BloodBankService;
import com.bnl.bloodbank.service.BloodBankServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
public class BloodBankTest {

    @Mock
    BloodBankRepository bloodBankRepository;

    @InjectMocks
    BloodBankService bloodBankService = new BloodBankServiceImpl();

    private static BloodBank bloodBank=
            BloodBank.builder()
                    .bloodBankId(1)
                    .name("raktkosh")
                    .state("Karnataka")
                    .city("Bangalore")
                    .address("DTP")
                    .mobileNumber(9876543210L)
                    .lastUpdated(LocalDate.now())
                    .bloodgroups(new ArrayList<BloodGroup>())
                    .build();

    private static BloodGroup bloodGroup =
            BloodGroup.builder()
                    .bloodGroupId(1)
                    .bloodGroup("A+")
                    .quantity(10)
                    .build();

    /**
     * To check new Blood bank is successfully created if unique mobile number is provided.
     * @throws AlreadyPresentException
     */
    @Test
    void validBloodBankRegistration() throws AlreadyPresentException{
        Mockito.when(bloodBankRepository.findByMobileNumber(bloodBank.getMobileNumber())).thenReturn(Optional.empty());
        Assertions.assertEquals(bloodBankService.addBloodBank(bloodBank), "Blood Bank " + bloodBank.getName() + " successfully saved");
    }

    /**
     * To check registerBloodBank throws AlreadyPresentException if mobile number is associated with some other user
     * @throws AlreadyPresentException
     */
    @Test
    void invalidBloodBankRegistration() throws AlreadyPresentException{
        Mockito.when(bloodBankRepository.findByMobileNumber(bloodBank.getMobileNumber())).thenReturn(Optional.of(bloodBank));
        AlreadyPresentException ex = Assertions.assertThrows(
                AlreadyPresentException.class,
                () -> bloodBankService.addBloodBank(bloodBank)
        );
        Assertions.assertEquals(ex.getMessage(), "Mobile number already present");
    }

    /**
     * To check if BloodBank details is returned if valid bloodBankId is provided
     * @throws NotPresentException
     */
    @Test
    void validGetBloodBank() throws NotPresentException{
        Mockito.when(bloodBankRepository.findById(bloodBank.getBloodBankId())).thenReturn(Optional.of(bloodBank));
        Assertions.assertEquals(bloodBankService.getBloodBank(bloodBank.getBloodBankId()), bloodBank);
    }

    /**
     * To check getBloodBank throws NotPresentException if bloodBankId provided is invalid
     * @throws NotPresentException
     */
    @Test
    void invalidGetBloodBank() throws NotPresentException{
        Mockito.when(bloodBankRepository.findById(bloodBank.getBloodBankId())).thenReturn(Optional.empty());
        NotPresentException ex = Assertions.assertThrows(
                NotPresentException.class,
                () -> bloodBankService.getBloodBank(bloodBank.getBloodBankId())
        );
        Assertions.assertEquals(ex.getMessage(), "Blood Bank with given ID not present");
    }

    /**
     * To check getBloodGroups returns bloodGroups if valid bloodBankId is provided
     * @throws NotPresentException
     */
    @Test
    void validGetBloodGroups() throws NotPresentException{
        Mockito.when(bloodBankRepository.findById(bloodBank.getBloodBankId())).thenReturn(Optional.of(bloodBank));
        Assertions.assertEquals(bloodBankService.getBloodGroups(bloodBank.getBloodBankId()), bloodBank.getBloodgroups());
    }

    /**
     * To check updateBloodBank is successful if bloodBankId is present in database and
     * mobileNumber is not associated with another user
     * @throws NotPresentException
     * @throws AlreadyPresentException
     */
    @Test
    void validUpdateBloodBank() throws NotPresentException, AlreadyPresentException{
        Mockito.when(bloodBankRepository.findById(bloodBank.getBloodBankId())).thenReturn(Optional.of(bloodBank));
        Mockito.when(bloodBankRepository.findByMobileNumber(bloodBank.getMobileNumber())).thenReturn(Optional.empty());
        Assertions.assertEquals(bloodBankService.updateBloodBank(bloodBank), "Blood Bank " + bloodBank.getName() + " successfully updated");
    }

    /**
     * To check the addition of blood group to a blood bank if bloodBankId is valid
     * @throws NotPresentException
     */
    @Test
    void validAddBloodGroup() throws NotPresentException{
        Mockito.when(bloodBankRepository.findById(bloodBank.getBloodBankId())).thenReturn(Optional.of(bloodBank));
        Assertions.assertEquals(bloodBankService.addBloodGroup(bloodBank.getBloodBankId(), bloodGroup) ,"Blood Group successfully added to blood bank : " + bloodBank.getName());
    }

    /**
     * To check deleteBloodGroup is successful if bloodBankId is valid
     * @throws NotPresentException
     */
    @Test
    void validDeleteBloodBank() throws NotPresentException{
        Mockito.when(bloodBankRepository.findById(bloodBank.getBloodBankId())).thenReturn(Optional.of(bloodBank));
        Assertions.assertEquals(bloodBankService.deleteBloodBank(bloodBank.getBloodBankId()), "Blood bank with ID : " + bloodBank.getBloodBankId() + " successfully deleted");
    }

}
