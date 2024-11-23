package org.example.airport.service;

import org.example.airport.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User registerService(User user);
    User deleteService(int id);
    User updateService(User user);
    Page<User> searchService(String query, Pageable pageable);
    List<User> getAllService();
}
