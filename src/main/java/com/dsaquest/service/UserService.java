package com.dsaquest.service;

import com.dsaquest.entity.User;
import java.util.List;

public interface UserService {
    User registerUser(User user);
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsersSortedByXp();
    void saveUser(User user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
