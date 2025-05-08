package com.example.controller

import com.example.model.User
import com.example.service.UserService
import jakarta.ws.rs.core.Response
import spock.lang.Specification

/**
 * Specification for testing the UserController class.
 * This test suite verifies that the UserController correctly interacts with the UserService
 * and properly handles HTTP requests and responses for all user management operations.
 * It ensures that appropriate HTTP status codes are returned for different scenarios.
 */
class UserControllerSpec extends Specification {

    UserService userService
    UserController userController

    def setup() {
        userService = Mock(UserService)
        userController = new UserController(userService)
    }

    /**
     * Test that verifies the getAllUsers endpoint correctly retrieves all users.
     * It ensures that the controller returns an OK response with the list of users.
     */
    def "should get all users"() {
        given: "a list of users and a mocked service response"
        def users = [
            new User("John Doe", "john@example.com", "1234567890"),
            new User("Jane Doe", "jane@example.com", "0987654321")
        ]
        userService.findAll() >> users

        when: "the getAllUsers endpoint is called"
        def response = userController.getAllUsers()

        then: "the response should have OK status and contain all users"
        response.status == Response.Status.OK.statusCode
        response.entity == users
        response.entity.size() == 2
    }

    /**
     * Test that verifies the getUserById endpoint correctly retrieves a user when it exists.
     * It ensures that the controller returns an OK response with the user.
     */
    def "should get user by id when user exists"() {
        given: "a user and a mocked service response for findById"
        def user = new User("John Doe", "john@example.com", "1234567890")
        userService.findById(1L) >> Optional.of(user)

        when: "the getUserById endpoint is called with a valid ID"
        def response = userController.getUserById(1L)

        then: "the response should have OK status and contain the user"
        response.status == Response.Status.OK.statusCode
        response.entity == user
    }

    /**
     * Test that verifies the getUserById endpoint correctly handles the case when a user doesn't exist.
     * It ensures that the controller returns a NOT_FOUND response.
     */
    def "should return not found when user does not exist"() {
        given: "a mocked service response returning empty Optional for findById"
        userService.findById(1L) >> Optional.empty()

        when: "the getUserById endpoint is called with an ID that doesn't exist"
        def response = userController.getUserById(1L)

        then: "the response should have NOT_FOUND status and null entity"
        response.status == Response.Status.NOT_FOUND.statusCode
        response.entity == null
    }

    /**
     * Test that verifies the createUser endpoint correctly creates a new user.
     * It ensures that the controller returns a CREATED response with the created user.
     */
    def "should create user"() {
        given: "a user to create"
        def user = new User("John Doe", "john@example.com", "1234567890")

        when: "the createUser endpoint is called"
        def response = userController.createUser(user)

        then: "the response should have CREATED status and contain the created user"
        response.status == Response.Status.CREATED.statusCode
        response.entity == user
        1 * userService.save(user) >> user
    }

    /**
     * Test that verifies the updateUser endpoint correctly updates an existing user.
     * It ensures that the controller returns an OK response with the updated user.
     */
    def "should update user when user exists"() {
        given: "a user to update and a mocked service response for existsById"
        def user = new User("John Doe", "john@example.com", "1234567890")
        userService.existsById(1L) >> true

        when: "the updateUser endpoint is called with a valid ID"
        def response = userController.updateUser(1L, user)

        then: "the response should have OK status and contain the updated user with correct properties"
        response.status == Response.Status.OK.statusCode
        response.entity.id == 1L
        response.entity.name == user.name
        response.entity.email == user.email
        1 * userService.save(_ as User) >> user
    }

    /**
     * Test that verifies the updateUser endpoint correctly handles the case when a user doesn't exist.
     * It ensures that the controller returns a NOT_FOUND response and doesn't call save.
     */
    def "should return not found when updating non-existent user"() {
        given: "a user to update and a mocked service response returning false for existsById"
        def user = new User("John Doe", "john@example.com", "1234567890")
        userService.existsById(1L) >> false

        when: "the updateUser endpoint is called with an ID that doesn't exist"
        def response = userController.updateUser(1L, user)

        then: "the response should have NOT_FOUND status and save should not be called"
        response.status == Response.Status.NOT_FOUND.statusCode
        0 * userService.save(_)
    }

    /**
     * Test that verifies the deleteUser endpoint correctly deletes an existing user.
     * It ensures that the controller returns a NO_CONTENT response and calls the service's deleteById method.
     */
    def "should delete user when user exists"() {
        given: "a mocked service response returning true for existsById"
        userService.existsById(1L) >> true

        when: "the deleteUser endpoint is called with a valid ID"
        def response = userController.deleteUser(1L)

        then: "the response should have NO_CONTENT status and deleteById should be called once"
        response.status == Response.Status.NO_CONTENT.statusCode
        1 * userService.deleteById(1L)
    }

    /**
     * Test that verifies the deleteUser endpoint correctly handles the case when a user doesn't exist.
     * It ensures that the controller returns a NOT_FOUND response and doesn't call deleteById.
     */
    def "should return not found when deleting non-existent user"() {
        given: "a mocked service response returning false for existsById"
        userService.existsById(1L) >> false

        when: "the deleteUser endpoint is called with an ID that doesn't exist"
        def response = userController.deleteUser(1L)

        then: "the response should have NOT_FOUND status and deleteById should not be called"
        response.status == Response.Status.NOT_FOUND.statusCode
        0 * userService.deleteById(_)
    }
}
