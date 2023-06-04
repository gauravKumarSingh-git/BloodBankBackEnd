package com.bnl.bloodbank;

import com.bnl.bloodbank.entity.Hospital;
import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.repository.HospitalRepository;
import com.bnl.bloodbank.service.HospitalService;
import com.bnl.bloodbank.service.HospitalServiceImpl;
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
     * To check hospital is registered if username is unique and mobile number is not registered to any other user
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
     * To check findByUsername is successful if username is present in database
     * @throws UsernameNotFoundException
     */
    @Test
    void validFindByUsername() throws UsernameNotFoundException{
        Mockito.when(hospitalRepository.findByUsername(hospital.getUsername())).thenReturn(Optional.of(hospital));
        Assertions.assertEquals(hospital, hospitalService.findByUsername(hospital.getUsername()));
    }

    /**
     * To check findByUsername throws UsernameNotFoundException if username is not present in database
     * @throws UsernameNotFoundException
     */
    @Test
    void invalidFindByUsername() throws UsernameNotFoundException{
        Mockito.when(hospitalRepository.findByUsername(hospital.getUsername())).thenReturn(Optional.empty());
        UsernameNotFoundException ex = Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> hospitalService.findByUsername(hospital.getUsername())
        );
        Assertions.assertEquals(ex.getMessage(), "Username " + hospital.getUsername() + " not found");
    }

    /**
     * To check updateHospital is successful if username is present in database
     * @throws AlreadyPresentException
     * @throws UsernameNotFoundException
     */
    @Test
    void validUpdateHospital() throws AlreadyPresentException, UsernameNotFoundException{
        Mockito.when(hospitalRepository.findByUsername(hospital.getUsername())).thenReturn(Optional.of(hospital));
        Assertions.assertEquals(hospitalService.updateHospital(hospital), "Successfully Updated hospital details with username : " + hospital.getUsername());
    }

    /**
     * To check deleteHospital is successful if username is present in database
     * @throws UsernameNotFoundException
     */
    @Test
    void validDeleteHospital() throws UsernameNotFoundException{
        Mockito.when(hospitalRepository.findByUsername(hospital.getUsername())).thenReturn(Optional.of(hospital));
        Assertions.assertEquals(hospitalService.deleteHospital(hospital.getUsername()), "Hospital with username: " + hospital.getUsername() + " successfully deleted");
    }

    /**
     * To check addRequest is successful if username is valid
     * @throws UsernameNotFoundException
     */
    @Test
    void validAddRequest() throws UsernameNotFoundException{
        Mockito.when(hospitalRepository.findByUsername(hospital.getUsername())).thenReturn(Optional.of(hospital));
        Assertions.assertEquals(hospitalService.addRequest(hospital.getUsername(), request), "Request succssfully added to : " + hospital.getUsername());
    }

    /**
     * To check getReqeusts is successful if username is valid
     * @throws UsernameNotFoundException
     */
    @Test
    void validGetReqeusts() throws UsernameNotFoundException{
        Mockito.when(hospitalRepository.findByUsername(hospital.getUsername())).thenReturn(Optional.of(hospital));
        Assertions.assertEquals(hospitalService.getRequests(hospital.getUsername()), hospital.getRequests());
    }


}
