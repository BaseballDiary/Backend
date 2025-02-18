package com.backend.baseball.Community.repository;

import com.backend.baseball.Community.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByPostCertificateId(Long postCertificateId);

    Page<Post> findByLikesGreaterThanEqual(int likes,Pageable pageable);

    Page<Post> findByTeamClub(String teamClub,Pageable pageable);

    Page<Post> findByTeamClubAndLikesGreaterThanEqual(String teamClub,int likes,Pageable pageable);
}
