package com.backend.baseball.Community.repository;

import com.backend.baseball.Community.entity.Post;
import com.backend.baseball.Community.entity.TeamCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Modifying
    @Query("UPDATE Post p SET p.views = p.views + 1 WHERE p.postCertificateId = :id")
    int updateView(Long id);

    Optional<Post> findByPostCertificateId(Long postCertificateId);

    Page<Post> findByTeamCategory(TeamCategory teamCategory, Pageable pageable);

}
