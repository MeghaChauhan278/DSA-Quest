package com.dsaquest.service.impl;

import com.dsaquest.entity.User;
import com.dsaquest.service.LevelService;
import org.springframework.stereotype.Service;

@Service
public class LevelServiceImpl implements LevelService {

    @Override
    public int calculateLevel(int totalXp) {
        if (totalXp >= 1000) return 5;
        if (totalXp >= 500) return 4;
        if (totalXp >= 250) return 3;
        if (totalXp >= 100) return 2;
        return 1;
    }

    @Override
    public void updateLevel(User user) {
        int newLevel = calculateLevel(user.getTotalXp());
        user.setCurrentLevel(newLevel);
    }
}
