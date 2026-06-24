package com.dsaquest.service.impl;

import com.dsaquest.entity.User;
import com.dsaquest.repository.UserRepository;
import com.dsaquest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAllUsersSortedByXp() {
        return userRepository.findAll(Sort.by(Sort.Direction.DESC, "totalXp"));
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
