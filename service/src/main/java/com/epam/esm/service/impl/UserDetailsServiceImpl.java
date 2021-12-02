package com.epam.esm.service.impl;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.entity.User;
import com.epam.esm.entity.dto.UserDetailsDto;
import com.epam.esm.service.exception.UnauthorizedRequestException;
import com.epam.esm.service.util.ExceptionMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    public static final String ANONYMOUS_USER = "anonymousUser";

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        UserDetailsDto userDetailsDto = new UserDetailsDto(user);
        return userDetailsDto;
    }

    public UserDetailsDto getAuthorizedUserDetails() throws UnauthorizedRequestException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals(ANONYMOUS_USER)) {
            throw new UnauthorizedRequestException(ExceptionMessageHandler.UNAUTHORIZED_REQUEST_MESSAGE_NAME);
        }
        return (UserDetailsDto) authentication.getPrincipal();
    }

}
