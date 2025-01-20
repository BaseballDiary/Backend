package com.backend.baseball.GameInfo.entity;

import com.backend.baseball.Login.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "dateCertificateId", nullable = false)
    private DateInfo dateInfo; //날짜 식별 번호
}
