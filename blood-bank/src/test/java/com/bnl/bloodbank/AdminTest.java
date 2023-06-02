package com.bnl.bloodbank;

import com.bnl.bloodbank.entity.Admin;
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

    /**
     * To test that admin data is saved to database if username is unique
     * @throws Exception
     */
    @Test
    void validAdminRegistration() throws Exception{
        Mockito.when(adminRepository.findByUsername(admin.getUsername())).thenReturn(Optional.empty());
        Assertions.assertEquals(adminService.registerAdmin(admin), "Admin with username " + admin.getUsername() + " successfully created");
    }

    /**
     * To test Exception is thrown if username is already present while creating a new admin.
     * @throws Exception
     */
    @Test
    void invalidAdminRegistration() throws Exception{
        Mockito.when(adminRepository.findByUsername(admin.getUsername())).thenReturn(Optional.of(admin));
        Exception ex = Assertions.assertThrows(
                Exception.class,
                () -> adminService.registerAdmin(admin)
        );
        Assertions.assertEquals(ex.getMessage(), "Admin with Username " + admin.getUsername() + " is already present");
    }

    /**
     * To check if findByUsername returns user if username is present.
     * @throws UsernameNotFoundException
     */
    @Test
    void validFindByUsername() throws UsernameNotFoundException{
        Mockito.when(adminRepository.findByUsername(admin.getUsername())).thenReturn(Optional.of(admin));
        Assertions.assertEquals(adminService.findByUsername(admin.getUsername()), admin);
    }

    /**
     * To check findByUsername throws UsernameNotFoundException if username not present
     * @throws UsernameNotFoundException
     */
    @Test
    void invalidFindByUsername() throws  UsernameNotFoundException{
        Mockito.when(adminRepository.findByUsername(admin.getUsername())).thenReturn(Optional.empty());
        UsernameNotFoundException ex = Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> adminService.findByUsername(admin.getUsername())
        );
        Assertions.assertEquals(ex.getMessage(), "Username " + admin.getUsername() + " not found");
    }

    /**
     * To check password is successfully updated if username present.
     * @throws UsernameNotFoundException
     */
    @Test
    void validUpdatePassword() throws UsernameNotFoundException{
        Mockito.when(adminRepository.findByUsername(admin.getUsername())).thenReturn(Optional.of(admin));
        Assertions.assertEquals(adminService.updatePassword(admin.getUsername(), admin.getPassword()), "Password Successfully Changed");
    }

    /**
     * To check password update throws UsernameNotFoundException if username not present in database.
     * @throws UsernameNotFoundException
     */
    @Test
    void invalidUpdatePassword() throws UsernameNotFoundException{
        Mockito.when(adminRepository.findByUsername(admin.getUsername())).thenReturn(Optional.empty());
        UsernameNotFoundException ex = Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> adminService.updatePassword(admin.getUsername(), admin.getPassword())
        );
        Assertions.assertEquals(ex.getMessage(), "Username " + admin.getUsername() + " not found");
    }

    /**
     * To check deleteAdmin successfully works when username present in database.
     * @throws UsernameNotFoundException
     */
    @Test
    void validDeleteAdmin() throws UsernameNotFoundException{
        Mockito.when(adminRepository.findByUsername(admin.getUsername())).thenReturn(Optional.of(admin));
        Assertions.assertEquals(adminService.deleteAdmin(admin.getUsername()), "Successfully Deleted admin with username : " + admin.getUsername());
    }

    /**
     * To check deleteAdmin throws UsernameNotFoundException if username not present in database.
     * @throws UsernameNotFoundException
     */
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
