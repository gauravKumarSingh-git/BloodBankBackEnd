package com.bnl.bloodbank.service;

import com.bnl.bloodbank.entity.Admin;
import com.bnl.bloodbank.exception.UsernameNotFoundException;

public interface AdminService {

    public String registerAdmin(Admin admin) throws Exception;

    public Admin findByUsername(String username) throws UsernameNotFoundException;

    public String updatePassword(String username, String password) throws UsernameNotFoundException;

    public String deleteAdmin(String username) throws UsernameNotFoundException;
    
}
