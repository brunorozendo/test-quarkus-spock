package com.example.service

import com.example.model.User
import com.example.repository.UserRepository
import io.quarkus.hibernate.orm.panache.Panache
import io.quarkus.hibernate.orm.panache.PanacheQuery
import io.quarkus.hibernate.orm.panache.runtime.JpaOperations
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

/**
 * Specification for testing the UserService class.
 * This test suite verifies that the UserService correctly interacts with the UserRepository
 * and properly implements all required business logic for user management operations.
 */
class UserServiceSpec extends Specification {


    UserRepository userRepository
    UserService userService;

    def setup() {
        userRepository = Mock(UserRepository)
        userService = new UserService(userRepository)
    }




    /**
     * Test that verifies the findAll method correctly retrieves all users from the repository.
     * It ensures that the service properly converts the PanacheQuery result to a list of users.
     */
    def "should find all users"() {
        given: "a list of users and a mocked repository response"
        def users = [
            new User("John Doe", "john@example.com", "1234567890"),
            new User("Jane Doe", "jane@example.com", "0987654321")
        ]
        PanacheQuery<User> a = Mock()
        a.stream() >>  users.stream();
        userRepository.findAll() >> a

        when: "the findAll method is called"
        def result = userService.findAll()

        then: "the result should contain all users from the repository"
        result == users
        result.size() == 2
    }

    /**
     * Test that verifies the findById method correctly retrieves a user by ID.
     * It ensures that the service returns a present Optional containing the user when found.
     */
    def "should find user by id"() {
        given: "a user and a mocked repository response for findById"
        def user = new User("John Doe", "john@example.com", "1234567890")
        userRepository.findById(1L) >> user

        when: "the findById method is called with a valid ID"
        def result = userService.findById(1L)

        then: "the result should be a present Optional containing the user"
        result.isPresent()
        result.get() == user
    }

    /**
     * Test that verifies the findById method correctly handles the case when a user is not found.
     * It ensures that the service returns an empty Optional when the repository returns null.
     */
    def "should return empty optional when user not found"() {
        given: "a mocked repository response returning null for findById"
        userRepository.findById(1L) >> null

        when: "the findById method is called with an ID that doesn't exist"
        def result = userService.findById(1L)

        then: "the result should be an empty Optional"
        !result.isPresent()
    }

    /**
     * Test that verifies the save method correctly persists a user.
     * It ensures that the service returns the saved user after persisting it.
     */
    def "should save user"() {
        given: "a mocked user"
        ///def user = new User("John Doe", "john@example.com", "1234567890")
        def user = Mock(User)
        user.persistAndFlush() >> null


        when: "the save method is called with the user"
        def result = userService.save(user)

        then: "the result should be the saved user and findById should be called once"
        result == user
        1 * userRepository.findById(_) >> user
    }

    /**
     * Test that verifies the deleteById method correctly deletes a user by ID.
     * It ensures that the service calls the repository's deleteById method with the correct ID.
     */
    def "should delete user by id"() {
        when: "the deleteById method is called with a valid ID"
        userService.deleteById(1L)

        then: "the repository's deleteById method should be called once with the same ID"
        1 * userRepository.deleteById(1L)
    }

    /**
     * Test that verifies the existsById method correctly checks if a user exists.
     * It ensures that the service returns true for existing users and false for non-existing users.
     */
    def "should check if user exists by id"() {
        given: "mocked repository responses for existsById"
        userRepository.existsById(1L) >> true
        userRepository.existsById(2L) >> false

        expect: "existsById to return true for ID 1 and false for ID 2"
        userService.existsById(1L)
        !userService.existsById(2L)
    }
}
