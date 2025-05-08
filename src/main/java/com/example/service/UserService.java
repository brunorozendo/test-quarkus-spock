package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;

import io.quarkus.hibernate.orm.panache.Panache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll().stream().toList();
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userRepository.findById(id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User save(User user) {
        user.persistAndFlush();
        return userRepository.findById(user.getId());
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
