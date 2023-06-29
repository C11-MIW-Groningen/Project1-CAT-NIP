package com.project1CATNIP.CATNIP.config;

/*
 *@Author: Marcel Tubben <mhg.tubben@st.hanze.nl>
 *
 *The Purpose
 */

import com.project1CATNIP.CATNIP.service.MIWUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class MIWSecurityConfiguration {

    private final MIWUserDetailsService miwUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((authorize) -> authorize
                        .antMatchers("/layout/**", "/webjars/**", "/images/**").permitAll()
                        .antMatchers("/", "/program/","/program/all", "/program/details/**").permitAll()
                        .antMatchers("/program/add", "/program/edit/**").hasAnyRole("ADMIN", "TEACHER")
                        .antMatchers("/program/delete/**").hasRole("ADMIN")
                        .antMatchers("/cohort/**").hasAnyRole("ADMIN", "TEACHER")
                        .antMatchers("/student/**").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                        .antMatchers("/teacher/add", "/teacher/delete/**", "/teacher/edit/**").hasRole("ADMIN")
                        .antMatchers("/course/add", "/course/delete/**", "/course/edit/**")
                        .hasAnyRole("ADMIN", "TEACHER")
                        .antMatchers("/test/**").hasAnyRole("ADMIN", "TEACHER")
                        .antMatchers("/grading/**").hasAnyRole("ADMIN", "TEACHER")
                        .antMatchers("/seed").hasRole("ADMIN")
                        .antMatchers("/assignment/**").hasAnyRole("ADMIN", "TEACHER")
                        .anyRequest().authenticated()
                )
                .formLogin().and()
                .logout().logoutSuccessUrl("/program/all");

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(miwUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}
