package com.dsaquest.service;

import com.dsaquest.entity.User;

public interface LevelService {
    int calculateLevel(int totalXp);
    void updateLevel(User user);
}
