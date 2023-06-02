package com.bnl.bloodbank;

import com.bnl.bloodbank.entity.BloodGroup;
import com.bnl.bloodbank.exception.AlreadyPresentException;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.repository.BloodGroupRepository;
import com.bnl.bloodbank.service.BloodGroupService;
import com.bnl.bloodbank.service.BloodGroupServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class BloodGroupTest {

    @Mock
    BloodGroupRepository bloodGroupRepository;

    @InjectMocks
    BloodGroupService bloodGroupService = new BloodGroupServiceImpl();

    private static BloodGroup bloodGroup =
            BloodGroup.builder()
                    .bloodGroupId(1)
                    .bloodGroup("AB-")
                    .quantity(11)
                    .build();

    @Test
    void validAddBloodGroup() throws AlreadyPresentException{
        Assertions.assertEquals(bloodGroupService.addBloodGroup(bloodGroup), "Blood Group " + bloodGroup.getBloodGroup() + " successfully saved");
    }

    @Test
    void validGetBloodGroup() throws NotPresentException{
        Mockito.when(bloodGroupRepository.findById(bloodGroup.getBloodGroupId())).thenReturn(Optional.of(bloodGroup));
        Assertions.assertEquals(bloodGroupService.getBloodGroup(bloodGroup.getBloodGroupId()), bloodGroup);
    }

    @Test
    void invalidGetBloodGroup() throws NotPresentException{
        Mockito.when(bloodGroupRepository.findById(bloodGroup.getBloodGroupId())).thenReturn(Optional.empty());
        NotPresentException ex = Assertions.assertThrows(
                NotPresentException.class,
                () -> bloodGroupService.getBloodGroup(bloodGroup.getBloodGroupId())
        );
        Assertions.assertEquals(ex.getMessage(), "Blood Group with ID : " + bloodGroup.getBloodGroupId() + " not present");
    }

    @Test
    void validDeleteBloodGroup() throws NotPresentException{
        Mockito.when(bloodGroupRepository.findById(bloodGroup.getBloodGroupId())).thenReturn(Optional.of(bloodGroup));
        Assertions.assertEquals(bloodGroupService.deleteBloodGroup(bloodGroup.getBloodGroupId()), "Blood Group with ID : " + bloodGroup.getBloodGroupId() + " successfully deleted");
    }

    @Test
    void invalidDeleteBloodGroup() throws NotPresentException{
        Mockito.when(bloodGroupRepository.findById(bloodGroup.getBloodGroupId())).thenReturn(Optional.empty());
        NotPresentException ex = Assertions.assertThrows(
                NotPresentException.class,
                () -> bloodGroupService.deleteBloodGroup(bloodGroup.getBloodGroupId())
        );
        Assertions.assertEquals(ex.getMessage(), "Blood Group with ID : " + bloodGroup.getBloodGroupId() + " not present");
    }

    @Test
    void validUpdateQuantity() throws NotPresentException{
        Mockito.when(bloodGroupRepository.findByBloodGroup(bloodGroup.getBloodGroup())).thenReturn(Optional.of(bloodGroup));
        Assertions.assertEquals(bloodGroupService.updateQuantity(bloodGroup.getBloodGroup(), bloodGroup.getQuantity()), "Successfully updated quantity for Blood Group " + bloodGroup.getBloodGroup());
    }

    @Test
    void validGetByBloodGroup() throws NotPresentException{
        Mockito.when(bloodGroupRepository.findByBloodGroup(bloodGroup.getBloodGroup())).thenReturn(Optional.of(bloodGroup));
        Assertions.assertEquals(bloodGroupService.getByBloodGroup(bloodGroup.getBloodGroup()), bloodGroup);
    }

    @Test
    void invalidGetByBloodGroup() throws NotPresentException{
        Mockito.when(bloodGroupRepository.findByBloodGroup(bloodGroup.getBloodGroup())).thenReturn(Optional.empty());
        NotPresentException ex = Assertions.assertThrows(
                NotPresentException.class,
                () -> bloodGroupService.getByBloodGroup(bloodGroup.getBloodGroup())
        );
        Assertions.assertEquals(ex.getMessage(), "Blood Group " + bloodGroup.getBloodGroup() + " not present");
    }
}
