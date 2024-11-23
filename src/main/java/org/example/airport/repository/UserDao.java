package org.example.airport.repository;

import org.example.airport.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserDao extends JpaRepository<User, Integer> {
    User findByUserName(String username);

    User findByUserNameAndUserPassword(String username, String password);

    User findById(int id);

    // 模糊查找用户，支持分页
    @Query("SELECT u FROM User u WHERE " +
            "u.userName LIKE %:query% OR " +
            "u.emailAddress LIKE %:query% OR " +
            "u.phoneNumber LIKE %:query% OR " +
            "u.gender LIKE %:query%")
    Page<User> findByFuzzyQuery(@Param("query") String query, Pageable pageable);
}

