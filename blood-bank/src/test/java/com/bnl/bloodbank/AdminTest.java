package com.bnl.bloodbank;

import com.bnl.bloodbank.entity.Admin;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.repository.AdminRepository;
import com.bnl.bloodbank.service.AdminService;
import com.bnl.bloodbank.service.AdminServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootTest
public class AdminTest {

    @Mock
    AdminRepository adminRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AdminService adminService = new AdminServiceImpl();

    private static Admin admin =
            Admin.builder()
                    .adminId(1)
                    .username("admin")
                    .password("admin")
                    .build();

    @Test
    void validAdminRegistration() throws Exception{
        Mockito.when(adminRepository.findByUsername(admin.getUsername())).thenReturn(Optional.empty());
        Assertions.assertEquals(adminService.registerAdmin(admin), "Admin with username " + admin.getUsername() + " successfully created");
    }

    @Test
    void invalidAdminRegistration() throws Exception{
        Mockito.when(adminRepository.findByUsername(admin.getUsername())).thenReturn(Optional.of(admin));
        Exception ex = Assertions.assertThrows(
                Exception.class,
                () -> adminService.registerAdmin(admin)
        );
        Assertions.assertEquals(ex.getMessage(), "Admin with Username " + admin.getUsername() + " is already present");
    }

    @Test
    void validFindByUsername() throws UsernameNotFoundException{
        Mockito.when(adminRepository.findByUsername(admin.getUsername())).thenReturn(Optional.of(admin));
        Assertions.assertEquals(adminService.findByUsername(admin.getUsername()), admin);
    }

    @Test
    void invalidFindByUsername() throws  UsernameNotFoundException{
        Mockito.when(adminRepository.findByUsername(admin.getUsername())).thenReturn(Optional.empty());
        UsernameNotFoundException ex = Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> adminService.findByUsername(admin.getUsername())
        );
        Assertions.assertEquals(ex.getMessage(), "Username " + admin.getUsername() + " not found");
    }

    @Test
    void validUpdatePassword() throws UsernameNotFoundException{
        Mockito.when(adminRepository.findByUsername(admin.getUsername())).thenReturn(Optional.of(admin));
        Assertions.assertEquals(adminService.updatePassword(admin.getUsername(), admin.getPassword()), "Password Successfully Changed");
    }

    @Test
    void invalidUpdatePassword() throws UsernameNotFoundException{
        Mockito.when(adminRepository.findByUsername(admin.getUsername())).thenReturn(Optional.empty());
        UsernameNotFoundException ex = Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> adminService.updatePassword(admin.getUsername(), admin.getPassword())
        );
        Assertions.assertEquals(ex.getMessage(), "Username " + admin.getUsername() + " not found");
    }

    @Test
    void validDeleteAdmin() throws UsernameNotFoundException{
        Mockito.when(adminRepository.findByUsername(admin.getUsername())).thenReturn(Optional.of(admin));
        Assertions.assertEquals(adminService.deleteAdmin(admin.getUsername()), "Successfully Deleted admin with username : " + admin.getUsername());
    }

    @Test
    void invalidDeleteAdmin() throws UsernameNotFoundException{
        Mockito.when(adminRepository.findByUsername(admin.getUsername())).thenReturn(Optional.empty());
        UsernameNotFoundException ex = Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> adminService.deleteAdmin(admin.getUsername())
        );
        Assertions.assertEquals(ex.getMessage(), "Username " + admin.getUsername() + " not found");
    }
}
