package com.dsaquest.controller;

import com.dsaquest.entity.Difficulty;
import com.dsaquest.entity.SolvedProblem;
import com.dsaquest.entity.User;
import com.dsaquest.service.ProblemService;
import com.dsaquest.service.StreakService;
import com.dsaquest.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final ProblemService problemService;
    private final StreakService streakService;

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        User currentUser = userService.getUserById(user.getId());
        streakService.updateStreak(currentUser);
        userService.saveUser(currentUser);
        
        List<SolvedProblem> recentProblems = problemService.getUserProblems(currentUser);
        
        long easyCount = recentProblems.stream().filter(p -> p.getDifficulty() == Difficulty.Easy).count();
        long mediumCount = recentProblems.stream().filter(p -> p.getDifficulty() == Difficulty.Medium).count();
        long hardCount = recentProblems.stream().filter(p -> p.getDifficulty() == Difficulty.Hard).count();

        model.addAttribute("user", currentUser);
        model.addAttribute("recentProblems", recentProblems.size() > 5 ? recentProblems.subList(0, 5) : recentProblems);
        model.addAttribute("easyCount", easyCount);
        model.addAttribute("mediumCount", mediumCount);
        model.addAttribute("hardCount", hardCount);

        return "dashboard";
    }
}
