package com.backend.baseball.Community.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamCategoryCertificateId;

    @Column(length=30)
    private String teamCategoryTitle;

    public TeamCategory(String teamCategoryTitle) {
        this.teamCategoryTitle = teamCategoryTitle;
    }
}
