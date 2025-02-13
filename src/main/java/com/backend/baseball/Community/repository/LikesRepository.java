package com.backend.baseball.Community.repository;

import com.backend.baseball.Community.entity.Likes;
import com.backend.baseball.Community.entity.Post;
import com.backend.baseball.Login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserAndPost(User user, Post post);
}
