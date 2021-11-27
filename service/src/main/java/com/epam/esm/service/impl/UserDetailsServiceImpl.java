package com.epam.esm.service.impl;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.entity.User;
import com.epam.esm.entity.dto.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        ApplicationUser applicationUser = new ApplicationUser(user);
        return applicationUser;
    }

}
