package com.example.repository;

import com.example.model.User;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class UserRepositoryTest {

    @Inject
    UserRepository userRepository;

    @Test
    @Transactional
    public void testFindByEmail_WhenUserExists() {
        // Given
        User user = new User("John Doe", "john@example.com", "123456789");
        userRepository.persist(user);

        // When
        User result = userRepository.findByEmail("john@example.com");

        // Then
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("123456789", result.getPhone());
    }

    @Test
    @Transactional
    public void testFindByEmail_WhenUserDoesNotExist() {
        // When
        User result = userRepository.findByEmail("nonexistent@example.com");

        // Then
        assertNull(result);
    }

    @Test
    @Transactional
    public void testExistsById_WhenUserExists() {
        // Given
        User user = new User("Jane Doe", "jane@example.com", "987654321");
        userRepository.persist(user);

        // When
        boolean exists = userRepository.existsById(user.getId());

        // Then
        assertTrue(exists);
    }

    @Test
    public void testExistsById_WhenUserDoesNotExist() {
        // When
        boolean exists = userRepository.existsById(999L);

        // Then
        assertFalse(exists);
    }

    @AfterEach
    @Transactional
    public void cleanup() {
        // Clean up after each test
        userRepository.deleteAll();
    }
}
