package com.backend.baseball.Diary.entity;

import com.backend.baseball.Diary.enums.ViewType;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.User.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long diaryId; // 일기 식별번호

    @Column
    private LocalDate date;

    @Column
    private String day; // 요일

    @Column(nullable = true)
    private ViewType viewType;

    @Column(nullable = false, columnDefinition = "TEXT DEFAULT '베볼리'")
    private String contents; // **기본값을 DB에서도 강제 적용**

    @Column
    private String score;

    @ElementCollection
    @CollectionTable(name = "diary_images", joinColumns = @JoinColumn(name = "diary_id"))
    @Column(name = "image_url")
    private List<String> imgUrls;

    @Transient
    private String year;

    public String getYear() {
        return date != null ? String.valueOf(date.getYear()) : null;
    }

    @PrePersist
    public void prePersist() {
        if (this.date == null) {
            this.date = LocalDate.now();
        }
    }

    @Builder
    public Diary(ViewType viewType, String contents, List<String> imgUrls) {
        this.viewType = viewType;
        this.contents = contents; // null 값 허용
        this.imgUrls = imgUrls; // null 값 허용
    }


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gameId", unique = true)
    private GameInfo gameInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "certificateId", nullable = false)
    private User user;

}
