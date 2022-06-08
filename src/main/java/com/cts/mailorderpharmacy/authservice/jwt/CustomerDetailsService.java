package com.cts.mailorderpharmacy.authservice.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.mailorderpharmacy.authservice.repository.UserRepository;
import com.cts.mailorderpharmacy.authservice.model.User;

@Service
public class CustomerDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String uid) {

        try
        {
            User custuser = userRepository.findById(uid).orElse(null);
            if(custuser != null) {
                custuser.getUname();
            }
            return new org.springframework.security.core.userdetails.User(custuser.getUserid(), custuser.getUpassword(), new ArrayList<>());
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }



    }

}
