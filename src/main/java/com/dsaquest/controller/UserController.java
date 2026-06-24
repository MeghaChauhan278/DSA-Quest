package com.dsaquest.controller;

import com.dsaquest.entity.User;
import com.dsaquest.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (userService.existsByUsername(user.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Username is already taken");
        }

        if (userService.existsByEmail(user.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "Email is already registered");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        try {
            User user = userService.getUserByUsername(username);
            if (passwordEncoder.matches(password, user.getPassword())) {
                session.setAttribute("user", user);
                return "redirect:/dashboard";
            } else {
                return "redirect:/login?error";
            }
        } catch (Exception e) {
            return "redirect:/login?error";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        user = userService.getUserById(user.getId());
        model.addAttribute("user", user);
        return "profile";
    }
}
