package com.dsaquest.service.impl;

import com.dsaquest.entity.User;
import com.dsaquest.repository.UserRepository;
import com.dsaquest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User registerUser(User user) {
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
}
