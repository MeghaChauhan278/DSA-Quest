package com.dsaquest.service.impl;

import com.dsaquest.entity.SolvedProblem;
import com.dsaquest.entity.User;
import com.dsaquest.repository.SolvedProblemRepository;
import com.dsaquest.service.StreakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StreakServiceImpl implements StreakService {

    private final SolvedProblemRepository problemRepository;

    @Override
    public void updateStreak(User user) {
        List<SolvedProblem> problems = problemRepository.findByUserOrderBySolvedDateDesc(user);
        
        List<LocalDate> dates = problems.stream()
                .map(SolvedProblem::getSolvedDate)
                .filter(Objects::nonNull)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .toList();

        int currentStreak = 0;
        if (!dates.isEmpty()) {
            LocalDate today = LocalDate.now();
            LocalDate latestDate = dates.get(0);
            
            // Check if the streak is broken (last solve was before yesterday)
            if (!latestDate.isBefore(today.minusDays(1))) {
                currentStreak = 1;
                for (int i = 1; i < dates.size(); i++) {
                    if (dates.get(i).equals(dates.get(i - 1).minusDays(1))) {
                        currentStreak++;
                    } else {
                        break;
                    }
                }
            }
        }
        
        user.setCurrentStreak(currentStreak);
        if (currentStreak > user.getLongestStreak()) {
            user.setLongestStreak(currentStreak);
        }
    }
}
