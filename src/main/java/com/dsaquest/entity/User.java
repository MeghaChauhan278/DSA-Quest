package com.dsaquest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please enter a valid email address")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private Integer totalXp = 0;

    private Integer currentLevel = 1;

    private Integer currentStreak = 0;

    private Integer longestStreak = 0;

    private Integer totalProblemsSolved = 0;

    private LocalDateTime joinDate = LocalDateTime.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SolvedProblem> solvedProblems;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserAchievement> userAchievements;
}
