package com.dsaquest.entity;

import jakarta.persistence.*;
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
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
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
