package com.bnl.bloodbank;

import com.bnl.bloodbank.entity.Hospital;
import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.repository.HospitalRepository;
import com.bnl.bloodbank.service.HospitalService;
import com.bnl.bloodbank.serviceImpl.HospitalServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
public class HospitalTest {

    @Mock
    HospitalRepository hospitalRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    HospitalService hospitalService =  new HospitalServiceImpl();

    private static Hospital hospital =
            Hospital.builder()
                    .hospitalId(1)
                    .name("max")
                    .username("max")
                    .password("password")
                    .state("Karnataka")
                    .city("Bangalore")
                    .address("address")
                    .mobileNumber(9876543210L)
                    .requests(new ArrayList<Request>())
                    .build();

    private static Request request =
            Request.builder()
                    .requestId(1)
                    .bloodGroup("A+")
                    .quantity(2)
                    .build();

    /**
     * To check hospital is registered if id is unique and mobile number is not registered to any other user
     * @throws AlreadyPresentException
     */
    @Test
    void validRegisterHospital() throws AlreadyPresentException{
        Mockito.when(hospitalRepository.findByUsername(hospital.getUsername())).thenReturn(Optional.empty());
        Mockito.when(hospitalRepository.findByMobileNumber(hospital.getMobileNumber())).thenReturn(Optional.empty());
        Assertions.assertEquals(hospitalService.registerHospital(hospital), "Hospital with username : " + hospital.getUsername() + " successfully registered");
    }

    /**
     * To check if registerHospital throws AlreadyPresentException if username is already present
     * @throws AlreadyPresentException
     */
    @Test
    void invalidRegisterHospitalUsernameAlreadyPresent() throws AlreadyPresentException{
        Mockito.when(hospitalRepository.findByUsername(hospital.getUsername())).thenReturn(Optional.of(hospital));
        AlreadyPresentException ex = Assertions.assertThrows(
                AlreadyPresentException.class,
                () -> hospitalService.registerHospital(hospital)
        );
        Assertions.assertEquals(ex.getMessage(), "Username alredy present");
    }

    /**
     * To check registerHospital throws AlreadyPresentException if mobile number is already present
     * @throws AlreadyPresentException
     */
    @Test
    void invalidRegisterHospitalMobileNumberAlreadyPresent() throws AlreadyPresentException{
        Mockito.when(hospitalRepository.findByMobileNumber(hospital.getMobileNumber())).thenReturn(Optional.of(hospital));
        AlreadyPresentException ex = Assertions.assertThrows(
                AlreadyPresentException.class,
                () -> hospitalService.registerHospital(hospital)
        );
        Assertions.assertEquals(ex.getMessage(), "Phone number already present");
    }

    /**
     * To check findById is successful if id is present in database
     * @throws NotPresentException
     */
    @Test
    void validFindById() throws NotPresentException {
        Mockito.when(hospitalRepository.findById(hospital.getHospitalId())).thenReturn(Optional.of(hospital));
        Assertions.assertEquals(hospital, hospitalService.findById(hospital.getHospitalId()));
    }

    /**
     * To check findByUsername throws NotPresentException if id is not present in database
     * @throws NotPresentException
     */
    @Test
    void invalidFindById() throws NotPresentException{
        Mockito.when(hospitalRepository.findById(hospital.getHospitalId())).thenReturn(Optional.empty());
        NotPresentException ex = Assertions.assertThrows(
                NotPresentException.class,
                () -> hospitalService.findById(hospital.getHospitalId())
        );
        Assertions.assertEquals(ex.getMessage(), "ID: " + hospital.getHospitalId() + " not found");
    }

    /**
     * To check updateHospital is successful if id is present in database
     * @throws AlreadyPresentException
     * @throws NotPresentException
     */
    @Test
    void validUpdateHospital() throws AlreadyPresentException, NotPresentException{
        Mockito.when(hospitalRepository.findById(hospital.getHospitalId())).thenReturn(Optional.of(hospital));
        Assertions.assertEquals(hospitalService.updateHospital(hospital), "Successfully Updated hospital details with ID: " + hospital.getHospitalId());
    }

    /**
     * To check deleteHospital is successful if id is present in database
     * @throws NotPresentException
     */
    @Test
    void validDeleteHospital() throws NotPresentException{
        Mockito.when(hospitalRepository.findById(hospital.getHospitalId())).thenReturn(Optional.of(hospital));
        Assertions.assertEquals(hospitalService.deleteHospital(hospital.getHospitalId()), "Hospital with ID: " + hospital.getHospitalId() + " successfully deleted");
    }

    /**
     * To check addRequest is successful if id is valid
     * @throws NotPresentException
     */
    @Test
    void validAddRequest() throws NotPresentException{
        Mockito.when(hospitalRepository.findById(hospital.getHospitalId())).thenReturn(Optional.of(hospital));
        Assertions.assertEquals(hospitalService.addRequest(hospital.getHospitalId(), request), "Request succssfully added to user with ID: " + hospital.getHospitalId());
    }

    /**
     * To check getReqeusts is successful if id is valid
     * @throws NotPresentException
     */
    @Test
    void validGetReqeusts() throws NotPresentException{
        Mockito.when(hospitalRepository.findById(hospital.getHospitalId())).thenReturn(Optional.of(hospital));
        Assertions.assertEquals(hospitalService.getRequests(hospital.getHospitalId()), hospital.getRequests());
    }


}
