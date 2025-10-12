package com.bunary.vocab.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.exception.ApiException;
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
            throw new ApiException(ErrorCode.USER_NOT_FOUND);

        if (user.isEmailVerified() == false)
            throw new ApiException(ErrorCode.USER_NOT_VERIFIED);

        return new User(user.getEmail(), user.getPassword(), new ArrayList());
    }

}
