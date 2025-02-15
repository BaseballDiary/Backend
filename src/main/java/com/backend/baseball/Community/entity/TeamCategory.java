package com.backend.baseball.Community.entity;

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
public class TeamCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamCategoryCertificateId;

    @Column(nullable = false)
    private String teamCategoryName;
}
