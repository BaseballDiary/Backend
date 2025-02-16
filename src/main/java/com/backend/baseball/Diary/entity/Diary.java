package com.backend.baseball.Diary.entity;

import com.backend.baseball.Diary.enums.ViewType;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.User.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private Long diaryId; //일기 식별번호

    @Column
    private LocalDate date;

    @Column
    private String day; //요일

    @Column(nullable = false)
    private ViewType viewType;

    @Column(nullable = false)
    private String contents;

    @ElementCollection
    @CollectionTable(name = "diary_images", joinColumns = @JoinColumn(name = "diary_id"))
    @Column(name = "image_url")
    private List<String> imgUrls;

    @Transient
    private String year;

    public String getYear(){
        return date !=null ? String.valueOf(date.getYear()) : null;
    }

    @PrePersist
    public void prePersist() {
        if (this.date == null) {
            this.date = LocalDate.now();
        }
    }

    @Builder
    public Diary(ViewType viewType, String content, List<String> imgUrl){
        this.viewType = viewType;
        this.contents = content;
        this.imgUrls = imgUrl;
    }

    @OneToOne
    @JoinColumn(name = "gameId", unique = true)
    private GameInfo gameInfo;

    @ManyToOne
    @JoinColumn(name = "certificateId", nullable = false)
    private User user;

    public void update(LocalDate date, ViewType viewType, String content, List<String> imgUrl, GameInfo gameInfo) {
        this.date = date;
        this.viewType = viewType;
        this.contents = content;
        this.imgUrls = imgUrl;
        this.gameInfo = gameInfo;
    }

    public void updateDiary(String contents, List<String> imgUrls) {
        if (contents != null) {
            this.contents = contents;
        }
        if (imgUrls != null) {
            this.imgUrls = imgUrls;
        }
    }

}
