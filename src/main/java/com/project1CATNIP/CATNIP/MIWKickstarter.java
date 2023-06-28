package com.project1CATNIP.CATNIP;

/*
 *@Author: Marcel Tubben <mhg.tubben@st.hanze.nl>
 *
 *The Purpose
 */

import com.project1CATNIP.CATNIP.model.MIWUser;
import com.project1CATNIP.CATNIP.repository.MIWUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class MIWKickstarter implements CommandLineRunner {

    private final MIWUserRepository miwUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (miwUserRepository.findByUsername("admin").isEmpty()) {
            MIWUser admin = new MIWUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("adminpw"));
            admin.setIsAdministrator(true);
            miwUserRepository.save(admin);
            System.err.println("Admin generated.");
        } else {
            System.err.println("Admin already exists.");
        }
    }
}
