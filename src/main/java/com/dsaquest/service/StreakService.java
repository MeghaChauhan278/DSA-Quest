package com.dsaquest.service;

import com.dsaquest.entity.User;
import java.time.LocalDate;

public interface StreakService {
    void updateStreak(User user, LocalDate solvedDate);
}
