package com.backend.baseball.Community.repository;

import com.backend.baseball.Community.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Modifying
    @Query("UPDATE Post p SET p.views = p.views + 1 WHERE p.postCertificateId = :id")
    int updateView(Long id);

    /** 📌 좋아요 증가 */
    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.likeCount = p.likeCount + 1 WHERE p.postCertificateId = :postCertificateId")
    void addLikeCount(Long postCertificateId);

    /** 📌 좋아요 감소 (최소 0 유지) */
    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.likeCount = CASE WHEN p.likeCount > 0 THEN p.likeCount - 1 ELSE 0 END WHERE p.postCertificateId = :postCertificateId")
    void subLikeCount(Long postCertificateId);

    Optional<Post> findByPostCertificateId(Long postCertificateId);

    Page<Post> findByTitleContaining(String keyword, Pageable pageable);

    //Page<Post> findByTeamClub(String teamClub, Pageable pageable);

    /** 특정 구단의 게시글 목록 조회 */
    @Query("SELECT p FROM Post p WHERE p.teamClub = :teamClub")
    Page<Post> findByTeamClub(@Param("teamClub") String teamClub, Pageable pageable);

    /** 특정 구단 내에서 키워드 검색 */
    @Query("SELECT p FROM Post p WHERE p.teamClub = :teamClub AND p.title LIKE %:keyword%")
    Page<Post> findByTeamClubAndTitleContaining(@Param("teamClub") String teamClub,
                                                @Param("keyword") String keyword,
                                                Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.likeCount >= 10 AND p.createdAt >= :timeLimit AND p.teamClub = :teamClub ORDER BY p.likeCount DESC")
    List<Post> findPopularPostsByTeam(@Param("timeLimit") LocalDateTime timeLimit, @Param("teamClub") String teamClub);




}
