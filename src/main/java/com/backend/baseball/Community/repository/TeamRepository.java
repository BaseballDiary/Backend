package com.backend.baseball.Community.repository;

import com.backend.baseball.Community.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
