package com.bunary.vocab.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bunary.vocab.code.ErrorCode;
import com.bunary.vocab.exception.ApiException;
import com.bunary.vocab.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.bunary.vocab.model.User user = userRepository.findByEmailJoinRolesAndPers(email)
                .orElseThrow(() -> new ApiException(ErrorCode.ID_NOT_FOUND));

        if (user == null)
            throw new ApiException(ErrorCode.USER_NOT_FOUND);

        if (user.isEmailVerified() == false)
            throw new ApiException(ErrorCode.USER_NOT_VERIFIED);

        return new CustomUserDetails(user);
    }

}
