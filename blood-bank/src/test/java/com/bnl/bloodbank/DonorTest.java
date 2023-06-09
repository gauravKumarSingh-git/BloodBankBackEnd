package com.bnl.bloodbank;

import com.bnl.bloodbank.entity.Donor;
import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.repository.DonorRepository;
import com.bnl.bloodbank.service.DonorService;
import com.bnl.bloodbank.serviceImpl.DonorServiceImpl;
import com.bnl.bloodbank.utility.UserRequestsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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

        private Donor donor = Donor.builder()
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
                    .requests( new ArrayList<Request>(Arrays.asList(
                            new Request(1L, "A+", 10L, LocalDate.now(), "pending", this.donor,null )
                    )))
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
     * To check updateDonor is successful if id is valid
     * @throws NotPresentException
     * @throws AlreadyPresentException
     */
    @Test
    void validUpdateDonor() throws NotPresentException, AlreadyPresentException {
        Mockito.when(donorRepository.findById(donor.getDonorId())).thenReturn(Optional.of(donor));
        Assertions.assertEquals(donorService.updateDonor(donor), "Successfully updated");
    }

    /**
     * To check addRequest is successful if id is valid
     * @throws NotPresentException
     */
    @Test
    void validAddRequest() throws NotPresentException{
        Mockito.when(donorRepository.findById(donor.getDonorId())).thenReturn(Optional.of(donor));
        Assertions.assertEquals(donorService.addRequest(donor.getDonorId(), request), "Request Successfully added to donor with ID: " + donor.getDonorId());
    }

    /**
     * To check deleteDonor is successful if id is valid
     * @throws NotPresentException
     */
    @Test
    void validDonorDelete() throws NotPresentException {
        Mockito.when(donorRepository.findById(donor.getDonorId())).thenReturn(Optional.of(donor));
        Assertions.assertEquals(donorService.deleteDonor(donor.getDonorId()), "Donor with ID: " + donor.getDonorId() + " successfully deleted");
    }

    /**
     * To check findById throws NotPresentException if id is not present in database.
     * @throws NotPresentException
     */
    @Test
    void invalidFindById() throws NotPresentException {
        Mockito.when(donorRepository.findById(donor.getDonorId())).thenReturn(Optional.empty());
        NotPresentException ex = Assertions.assertThrows(
                NotPresentException.class,
                () -> donorService.findById(donor.getDonorId())
        );
        Assertions.assertEquals(ex.getMessage(), "ID: " + donor.getDonorId() + " not found");
    }

    /**
     * To check getRequest is successful if id is valid
     * @throws NotPresentException
     */
    @Test
    void validGetRequest() throws NotPresentException {
        Mockito.when(donorRepository.findById(donor.getDonorId())).thenReturn(Optional.of(donor));
        Mockito.when(donorRepository.findRequestsById(donor.getDonorId())).thenReturn(donor.getRequests());
        Assertions.assertEquals(donorService.getRequests(donor.getDonorId()), donor.getRequests());
    }

    @Test
    void validGetUserAndRequestDetails() {
        Mockito.when(donorRepository.getUserAndRequestDetails()).thenReturn(List.of(donor));
        List<UserRequestsResponse> res = donorService.getUserAndRequestDetails();
        res.forEach(userReq -> {
            Assertions.assertEquals(userReq.getStatus(), "pending");
        });
    }

}
