package com.dsaquest.controller;

import com.dsaquest.entity.User;
import com.dsaquest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LeaderboardController {

    private final UserService userService;

    @GetMapping("/leaderboard")
    public String showLeaderboard(Model model) {
        List<User> topUsers = userService.getAllUsersSortedByXp();
        model.addAttribute("users", topUsers);
        return "leaderboard";
    }
}
