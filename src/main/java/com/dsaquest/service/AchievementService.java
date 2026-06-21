package com.dsaquest.service;

import com.dsaquest.entity.User;
import com.dsaquest.entity.UserAchievement;
import java.util.List;

public interface AchievementService {
    void checkAndAwardAchievements(User user);
    List<UserAchievement> getUserAchievements(User user);
}
