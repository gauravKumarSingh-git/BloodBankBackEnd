package com.bnl.bloodbank.service;

import com.bnl.bloodbank.entity.Admin;
import com.bnl.bloodbank.exception.UsernameNotFoundException;

public interface AdminService {
    /**
     * To register a new admin. The username should not be already present in database otherwise
     * Exception will be thrown
     * @param admin
     * @return String
     * @throws Exception
     */
    public String registerAdmin(Admin admin) throws Exception;

    /**
     * To get details of admin by username. Username should be present in database otherwise
     * UsernameNotFoundException will be thrown
     * @param id
     * @return
     * @throws UsernameNotFoundException
     */
    public Admin findById(long id) throws UsernameNotFoundException;

    /**
     * To update password of admin. The password will be encrypted first and then stored to database
     * @param id
     * @param password
     * @return String
     * @throws UsernameNotFoundException
     */
    public String updatePassword(long id, String password) throws UsernameNotFoundException;

    /**
     * To delete details of admin by id. The id should be present in database
     * @param id
     * @return String
     * @throws UsernameNotFoundException
     */
    public String deleteAdmin(long id) throws UsernameNotFoundException;
    
}
