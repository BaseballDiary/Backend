package com.backend.baseball.Community.repository;

import com.backend.baseball.Community.entity.TeamCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamCategoryRepository extends JpaRepository<TeamCategory, Long> {
    Optional<TeamCategory> findByTeamCategoryTitle(String teamCategoryTitle);
    List<TeamCategory> findAll();
}
