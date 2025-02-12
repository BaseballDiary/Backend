package com.backend.baseball.Home.entity;

import com.backend.baseball.Login.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attCertificateId; //출석 식별 번호

    @ManyToOne
    @JoinColumn(name = "certificateId", nullable = false)
    private User user; //회원 식별 번호

    @Column(nullable = false)
    private LocalDate date; //날짜 식별 번호

    @Column(nullable = false)
    private Boolean isAttendance; //출석 여부

}
