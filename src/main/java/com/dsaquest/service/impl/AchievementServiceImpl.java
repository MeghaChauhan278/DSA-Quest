package com.dsaquest.service.impl;

import com.dsaquest.entity.Achievement;
import com.dsaquest.entity.Difficulty;
import com.dsaquest.entity.SolvedProblem;
import com.dsaquest.entity.User;
import com.dsaquest.entity.UserAchievement;
import com.dsaquest.repository.AchievementRepository;
import com.dsaquest.repository.UserAchievementRepository;
import com.dsaquest.repository.SolvedProblemRepository;
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
    private final SolvedProblemRepository solvedProblemRepository;

    @Override
    public void checkAndAwardAchievements(User user) {
        checkProblemCountAchievements(user);
        checkStreakAchievements(user);
        checkDifficultyAndTopicAchievements(user);
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

    private void checkDifficultyAndTopicAchievements(User user) {
        List<SolvedProblem> problems = solvedProblemRepository.findByUserOrderBySolvedDateDesc(user);
        
        long hardCount = problems.stream()
                .filter(p -> p.getDifficulty() == Difficulty.Hard)
                .count();
        if (hardCount >= 10) {
            awardIfNew(user, "Hardcore Solver");
        }
        
        long arrayCount = problems.stream()
                .filter(p -> p.getTopic() != null && 
                        (p.getTopic().equalsIgnoreCase("Array") || p.getTopic().equalsIgnoreCase("Arrays")))
                .count();
        if (arrayCount >= 15) {
            awardIfNew(user, "Topic Master: Arrays");
        }
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
