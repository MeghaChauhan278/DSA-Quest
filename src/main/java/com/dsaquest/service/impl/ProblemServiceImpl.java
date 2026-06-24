package com.dsaquest.service.impl;

import com.dsaquest.entity.Difficulty;
import com.dsaquest.entity.SolvedProblem;
import com.dsaquest.entity.User;
import com.dsaquest.repository.SolvedProblemRepository;
import com.dsaquest.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {

    private final SolvedProblemRepository problemRepository;
    private final UserService userService;
    private final LevelService levelService;
    private final StreakService streakService;
    private final AchievementService achievementService;

    @Override
    @Transactional
    public SolvedProblem addSolvedProblem(SolvedProblem problem, User user) {
        int xp = calculateXp(problem.getDifficulty());
        problem.setXpEarned(xp);
        problem.setUser(user);
        SolvedProblem savedProblem = problemRepository.save(problem);

        user.setTotalXp(user.getTotalXp() + xp);
        user.setTotalProblemsSolved(user.getTotalProblemsSolved() + 1);
        levelService.updateLevel(user);
        streakService.updateStreak(user);
        achievementService.checkAndAwardAchievements(user);
        userService.saveUser(user);
        return savedProblem;
    }

    private int calculateXp(Difficulty difficulty) {
        return switch (difficulty) {
            case Easy -> 10;
            case Medium -> 25;
            case Hard -> 50;
        };
    }

    @Override
    public List<SolvedProblem> getUserProblems(User user) {
        return problemRepository.findByUserOrderBySolvedDateDesc(user);
    }

    @Override
    @Transactional
    public void deleteProblem(Long problemId, User user) {
        SolvedProblem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new RuntimeException("Problem not found"));
        if (!problem.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to delete this problem");
        }

        int xp = problem.getXpEarned() != null ? problem.getXpEarned() : 0;
        user.setTotalXp(Math.max(0, user.getTotalXp() - xp));
        user.setTotalProblemsSolved(Math.max(0, user.getTotalProblemsSolved() - 1));
        
        levelService.updateLevel(user);
        
        problemRepository.delete(problem);
        
        streakService.updateStreak(user);
        userService.saveUser(user);
    }
}
