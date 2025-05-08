package com.example.repository

import com.example.model.User
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import spock.lang.Specification

/**
 * Specification for testing the UserRepository class.
 * This test suite verifies that the UserRepository correctly interacts with the database
 * and properly handles user data operations.
 */
@QuarkusTest
class UserRepositorySpec extends Specification {

    @Inject
    UserRepository userRepository

    /**
     * Test that verifies the findByEmail method correctly retrieves a user when it exists.
     * It ensures that the repository returns the user with the correct properties.
     */
    def "should find user by email when user exists"() {
        given: "a user persisted in the database"
        def user = new User("John Doe", "john@example.com", "123456789")
        userRepository.persist(user)

        when: "findByEmail is called with an existing email"
        def result = userRepository.findByEmail("john@example.com")

        then: "the result should not be null and have the correct properties"
        result != null
        result.name == "John Doe"
        result.email == "john@example.com"
        result.phone == "123456789"
    }

    /**
     * Test that verifies the findByEmail method correctly handles the case when a user doesn't exist.
     * It ensures that the repository returns null.
     */
    def "should return null when finding user by email that does not exist"() {
        when: "findByEmail is called with a non-existent email"
        def result = userRepository.findByEmail("nonexistent@example.com")

        then: "the result should be null"
        result == null
    }

    /**
     * Test that verifies the existsById method correctly identifies when a user exists.
     * It ensures that the repository returns true.
     */
    def "should return true when checking if user exists by id and user exists"() {
        given: "a user persisted in the database"
        def user = new User("Jane Doe", "jane@example.com", "987654321")
        userRepository.persist(user)

        when: "existsById is called with an existing id"
        def exists = userRepository.existsById(user.id)

        then: "the result should be true"
        exists
    }

    /**
     * Test that verifies the existsById method correctly identifies when a user doesn't exist.
     * It ensures that the repository returns false.
     */
    def "should return false when checking if user exists by id and user does not exist"() {
        when: "existsById is called with a non-existent id"
        def exists = userRepository.existsById(999L)

        then: "the result should be false"
        !exists
    }

    /**
     * Cleanup method that runs after each test to ensure the database is in a clean state.
     */
    void cleanup() {
        // Clean up after each test
        userRepository.deleteAll()
    }
}
