package com.project1CATNIP.CATNIP.service;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * UserDetailsService for project
 */

import com.project1CATNIP.CATNIP.repository.MIWUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MIWUserDetailsService implements UserDetailsService {
    private final MIWUserRepository miwUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return miwUserRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username does not exist."));
    }
}
