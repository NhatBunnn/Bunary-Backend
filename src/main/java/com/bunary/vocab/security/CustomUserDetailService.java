package com.bunary.vocab.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bunary.vocab.exception.GlobalErrorCode;
import com.bunary.vocab.exception.CustomException.BadCredentialsException;
import com.bunary.vocab.service.user.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.bunary.vocab.model.User user = userService.findByEmail(username);

        if (user == null)
            throw new BadCredentialsException(GlobalErrorCode.USER_NOT_FOUND);

        if (user.isEmailVerified() == false)
            throw new BadCredentialsException(GlobalErrorCode.USER_INVALID);

        return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

}
