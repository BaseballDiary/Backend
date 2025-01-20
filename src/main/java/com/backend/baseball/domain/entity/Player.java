package com.backend.baseball.domain.entity;

import com.backend.baseball.domain.enums.Club;
import com.backend.baseball.domain.enums.PlayerRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerCertificateId; // 회원식별번호

    @Column(nullable = false, unique = true,length=30)
    private String playerNameK; // 선수 이름 한글

    @Column(nullable = false, unique = true,length=30)
    private String playerNameE; // 선수 이름 영어

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Club club; // 소속 구단

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlayerRole playerRole; // 타자 or 포수
    // 이미지는 아직..
}
