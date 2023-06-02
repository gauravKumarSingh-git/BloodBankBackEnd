package com.bnl.bloodbank;

import com.bnl.bloodbank.entity.Donor;
import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.repository.DonorRepository;
import com.bnl.bloodbank.service.DonorService;
import com.bnl.bloodbank.service.DonorServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class DonorTest {

    @Mock
    DonorRepository donorRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    DonorService donorService = new DonorServiceImpl();

        private static Donor donor = Donor.builder()
                    .donorId(1)
                    .address("DTP")
                    .city("Bangalore")
                    .state("Karnataka")
                    .gender("Male")
                    .email("donor@gmail.com")
                    .password("password")
                    .username("donor")
                    .phoneNumber(1234567890L)
                    .dateOfBirth(LocalDate.of(2000, 10, 11))
                    .requests( new ArrayList<Request>())
                    .build();

    private static Request request =
            Request.builder()
                    .requestId(1)
                    .bloodGroup("A+")
                    .quantity(2)
                    .build();

    /**
     * To check donor registration is successful if username is unique and phone number does not belong to any other donor
     * @throws AlreadyPresentException
     */
    @Test
    void validDonorRegistration() throws AlreadyPresentException{
        Mockito.when(donorRepository.findByUsername(donor.getUsername())).thenReturn(Optional.empty());
        Mockito.when(donorRepository.findByPhoneNumber(donor.getPhoneNumber())).thenReturn(Optional.empty());
        Assertions.assertEquals(donorService.registerDonor(donor) ,"Donor with username : " + donor.getUsername() + " successfully registered");
    }

    /**
     * To check registerDonor throws AlreadyPresentException if username already present
     * @throws AlreadyPresentException
     */
    @Test
    void invalidDonorRegistrtionUsernameAlreadyPresent() throws  AlreadyPresentException{
        Mockito.when(donorRepository.findByUsername(donor.getUsername())).thenReturn(Optional.of(donor));
        AlreadyPresentException ex = Assertions.assertThrows(
                    AlreadyPresentException.class,
                    () -> donorService.registerDonor(donor)
        );
        Assertions.assertEquals(ex.getMessage(), "Username alredy present");
    }

    /**
     * To check registerDonor throws AlreadyPresentException if Mobile number is registered with some other donor
     * @throws AlreadyPresentException
     */
    @Test
    void invalidDonorRegistrationPhoneNumberAlreadyPresent() throws AlreadyPresentException{
        Mockito.when(donorRepository.findByPhoneNumber(donor.getPhoneNumber())).thenReturn(Optional.of(donor));
        AlreadyPresentException ex = Assertions.assertThrows(
                AlreadyPresentException.class,
                () -> donorService.registerDonor(donor)
        );
        Assertions.assertEquals(ex.getMessage(), "Phone number already present");
    }

    /**
     * To check updateDonor is successful if username is valid
     * @throws UsernameNotFoundException
     * @throws AlreadyPresentException
     */
    @Test
    void validUpdateDonor() throws UsernameNotFoundException, AlreadyPresentException {
        Mockito.when(donorRepository.findByUsername(donor.getUsername())).thenReturn(Optional.of(donor));
        Assertions.assertEquals(donorService.updateDonor(donor), "Successfully updated");
    }

    /**
     * To check addRequest is successful if username is valid
     * @throws UsernameNotFoundException
     */
    @Test
    void validAddRequest() throws UsernameNotFoundException{
        Mockito.when(donorRepository.findByUsername(donor.getUsername())).thenReturn(Optional.of(donor));
        Assertions.assertEquals(donorService.addRequest(donor.getUsername(), request), "Request Successfully added to : " + donor.getUsername());
    }

    /**
     * To check deleteDonor is successful if username is valid
     * @throws UsernameNotFoundException
     */
    @Test
    void validDonorDelete() throws UsernameNotFoundException {
        Mockito.when(donorRepository.findByUsername(donor.getUsername())).thenReturn(Optional.of(donor));
        Assertions.assertEquals(donorService.deleteDonor(donor.getUsername()), "Donor with username : " + donor.getUsername() + " successfully deleted");
    }

    /**
     * To check findByUsername throws UsernameNotPresentException if username is not present in database.
     * @throws UsernameNotFoundException
     */
    @Test
    void invalidFindByUsername() throws UsernameNotFoundException {
        Mockito.when(donorRepository.findByUsername(donor.getUsername())).thenReturn(Optional.empty());
        UsernameNotFoundException ex = Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> donorService.findByUsername(donor.getUsername())
        );
    }

    /**
     * To check getRequest is successful if username is valid
     * @throws UsernameNotFoundException
     */
    @Test
    void validGetRequest() throws UsernameNotFoundException {
        Mockito.when(donorRepository.findByUsername(donor.getUsername())).thenReturn(Optional.of(donor));
        Assertions.assertEquals(donorService.getRequests(donor.getUsername()), donor.getRequests());
    }


}
