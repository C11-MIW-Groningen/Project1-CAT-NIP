package com.project1CATNIP.CATNIP.repository;

import com.project1CATNIP.CATNIP.model.MIWUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MIWUserRepository extends JpaRepository<MIWUser, Long> {
    Optional<MIWUser> findByUsername(String username);
}
