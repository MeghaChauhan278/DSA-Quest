package com.dsaquest.service.impl;

import com.dsaquest.entity.User;
import com.dsaquest.service.StreakService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class StreakServiceImpl implements StreakService {

    @Override
    public void updateStreak(User user, LocalDate solvedDate) {
        user.setCurrentStreak(user.getCurrentStreak() + 1);
        if (user.getCurrentStreak() > user.getLongestStreak()) {
            user.setLongestStreak(user.getCurrentStreak());
        }
    }
}
