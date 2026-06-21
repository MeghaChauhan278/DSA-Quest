package com.dsaquest.service.impl;

import com.dsaquest.entity.Achievement;
import com.dsaquest.entity.User;
import com.dsaquest.entity.UserAchievement;
import com.dsaquest.repository.AchievementRepository;
import com.dsaquest.repository.UserAchievementRepository;
import com.dsaquest.service.AchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;

    @Override
    public void checkAndAwardAchievements(User user) {
        checkProblemCountAchievements(user);
        checkStreakAchievements(user);
    }

    private void checkProblemCountAchievements(User user) {
        int solved = user.getTotalProblemsSolved();
        if (solved >= 1) awardIfNew(user, "First Step");
        if (solved >= 10) awardIfNew(user, "Decathlon");
        if (solved >= 50) awardIfNew(user, "Half Century");
        if (solved >= 100) awardIfNew(user, "Centurion");
    }

    private void checkStreakAchievements(User user) {
        int streak = user.getCurrentStreak();
        if (streak >= 7) awardIfNew(user, "Consistency King");
        if (streak >= 30) awardIfNew(user, "Monthly Master");
    }

    private void awardIfNew(User user, String achievementName) {
        achievementRepository.findByName(achievementName).ifPresent(achievement -> {
            boolean alreadyHas = userAchievementRepository.findByUserAndAchievement(user, achievement).isPresent();
            if (!alreadyHas) {
                UserAchievement ua = UserAchievement.builder()
                        .user(user)
                        .achievement(achievement)
                        .earnedDate(LocalDateTime.now())
                        .build();
                userAchievementRepository.save(ua);
                if (achievement.getXpReward() != null) {
                    user.setTotalXp(user.getTotalXp() + achievement.getXpReward());
                }
            }
        });
    }

    @Override
    public List<UserAchievement> getUserAchievements(User user) {
        return userAchievementRepository.findByUser(user);
    }
}
