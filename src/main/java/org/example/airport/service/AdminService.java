package org.example.airport.service;

import org.example.airport.domain.Admin;

public interface AdminService {
    Admin loginService(String username, String password);
    Admin registerService(Admin admin);
}
