package com.dsaquest.service;

import com.dsaquest.entity.SolvedProblem;
import com.dsaquest.entity.User;
import java.util.List;

public interface ProblemService {
    SolvedProblem addSolvedProblem(SolvedProblem problem, User user);
    List<SolvedProblem> getUserProblems(User user);
    void deleteProblem(Long problemId);
}
