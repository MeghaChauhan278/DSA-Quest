package com.dsaquest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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

    @NotBlank(message = "Problem title cannot be empty")
    private String title;

    private String problemLink;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Difficulty is required")
    private Difficulty difficulty;

    private String topic;

    private String platform;

    @NotNull(message = "Date solved is required")
    @PastOrPresent(message = "Date solved cannot be in the future")
    private LocalDate solvedDate;

    private Integer xpEarned;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
