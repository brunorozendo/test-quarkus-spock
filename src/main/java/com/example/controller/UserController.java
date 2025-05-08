package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {

    private final UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GET
    public Response getAllUsers() {
        List<User> users = userService.findAll();
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(value -> Response.ok(value).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response createUser(@Valid User user) {
        User savedUser = userService.save(user);
        return Response.status(Response.Status.CREATED).entity(savedUser).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") Long id, @Valid User user) {
        if (!userService.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        user.setId(id);
        User updatedUser = userService.save(user);
        return Response.ok(updatedUser).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        if (!userService.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        userService.deleteById(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
