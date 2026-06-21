package com.dsaquest.controller;

import com.dsaquest.entity.Difficulty;
import com.dsaquest.entity.SolvedProblem;
import com.dsaquest.entity.User;
import com.dsaquest.service.ProblemService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping
    public String listProblems(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        List<SolvedProblem> problems = problemService.getUserProblems(user);
        model.addAttribute("problems", problems);
        return "problems-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("problem", new SolvedProblem());
        model.addAttribute("difficulties", Difficulty.values());
        return "add-problem";
    }

    @PostMapping("/add")
    public String addProblem(@ModelAttribute("problem") SolvedProblem problem, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        problemService.addSolvedProblem(problem, user);
        return "redirect:/problems";
    }

    @GetMapping("/delete/{id}")
    public String deleteProblem(@PathVariable Long id) {
        problemService.deleteProblem(id);
        return "redirect:/problems";
    }
}
