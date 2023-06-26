package com.project1CATNIP.CATNIP.repository;

import com.project1CATNIP.CATNIP.model.MIWUser;

import java.util.Optional;

public interface MIWUserRepository {
    Optional<MIWUser> findByUsername(String username);
}
