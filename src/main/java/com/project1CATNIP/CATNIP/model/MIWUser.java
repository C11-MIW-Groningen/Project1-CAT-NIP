package com.project1CATNIP.CATNIP.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Authors: Saskia Tadema <s.tadema@st.hanze.nl>, Marcel Tubben <mhg.tubben@st.hanze.nl>
 * Somebody who can login into the system.
 */

@Entity
@Getter @Setter
public class MIWUser implements UserDetails {

    @Id
    @GeneratedValue
    private Long userId;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    private Boolean isAdministrator = false;

    private Boolean isTeacher = false;

    @OneToOne
    private Student student;

    @OneToOne
    private Teacher teacher;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();

        if (isAdministrator) {
            authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (isTeacher) {
            authorityList.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
        } else {
            authorityList.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        }

        return authorityList;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
