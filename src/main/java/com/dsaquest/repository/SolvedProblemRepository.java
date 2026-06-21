package com.dsaquest.repository;

import com.dsaquest.entity.SolvedProblem;
import com.dsaquest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SolvedProblemRepository extends JpaRepository<SolvedProblem, Long> {
    List<SolvedProblem> findByUserOrderBySolvedDateDesc(User user);
}
