package com.dsaquest.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "solved_problems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolvedProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String problemLink;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private String topic;

    private String platform;

    private LocalDate solvedDate;

    private Integer xpEarned;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
