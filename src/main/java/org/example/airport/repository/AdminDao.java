package org.example.airport.repository;

import org.example.airport.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminDao extends JpaRepository<Admin, Integer> {
    Admin findByAdminName(String username);
    Admin findByAdminNameAndAdminPassword(String username, String password);
    boolean existsByAdminName(String username);
}
