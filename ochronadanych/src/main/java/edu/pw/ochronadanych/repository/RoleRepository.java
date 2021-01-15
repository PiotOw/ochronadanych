package edu.pw.ochronadanych.repository;

import edu.pw.ochronadanych.entity.Role;
import edu.pw.ochronadanych.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
