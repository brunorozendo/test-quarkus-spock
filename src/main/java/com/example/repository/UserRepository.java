package com.example.repository;

import com.example.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

/**
 * Repository for User entity using Quarkus Panache.
 */
@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    /**
     * Find a user by email.
     * 
     * @param email the email to search for
     * @return the user with the given email or null if not found
     */
    public User findByEmail(String email) {
        return find("email", email).firstResult();
    }

    /**
     * Check if a user with the given id exists.
     * 
     * @param id the id to check
     * @return true if a user with the given id exists, false otherwise
     */
    public boolean existsById(Long id) {
        return count("id = ?1", id) > 0;
    }
}
