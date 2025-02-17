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
    private List<String> imgUrls = new ArrayList<>(List.of(
            "https://s3-alpha-sig.figma.com/img/8ee5/0c2e/b058dc79ca1625d68efd4664511165e3?Expires=1740960000&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=h~LN0GeB2EHXqKc7rGSxUDJvgZz~DZ2m0F6ET-OgqXREZXTRqRgYgvKDzjX1WxSeptpv0sNn9zNKqVJyDP4m2MT4VAIDZJb7Tzg57u0LGC~3Q3H3U3hLOP7mTYDCeCdPlJRV~1W2K8oYuIB68T3t-qZ0s6-6H5QGWmIqMjWDErIPWSZZdsjRU-htyx3jc49uzaPnaxZkr2YV-8hQJet8h-TI9usgTrWF37lFqZaGUcMeiaAQbN2sw9WJ3US1oeS6JLtZOLg4mXXGK2fWYsRxxUqBl0zV~NSeIjeyyjktzXPbzBiwh1BEJAHtXCWV0GEU463mMcRm2tp4BFqi7giY2g__"
    )); // 기본값 적용

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
        if (this.contents == null || this.contents.isEmpty()) {
            this.contents = "베볼리";  // **JPA에서 기본값 강제 적용**
        }
        if (this.imgUrls == null || this.imgUrls.isEmpty()) {
            this.imgUrls = new ArrayList<>(List.of(
                    "https://s3-alpha-sig.figma.com/img/8ee5/0c2e/b058dc79ca1625d68efd4664511165e3?Expires=1740960000&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=h~LN0GeB2EHXqKc7rGSxUDJvgZz~DZ2m0F6ET-OgqXREZXTRqRgYgvKDzjX1WxSeptpv0sNn9zNKqVJyDP4m2MT4VAIDZJb7Tzg57u0LGC~3Q3H3U3hLOP7mTYDCeCdPlJRV~1W2K8oYuIB68T3t-qZ0s6-6H5QGWmIqMjWDErIPWSZZdsjRU-htyx3jc49uzaPnaxZkr2YV-8hQJet8h-TI9usgTrWF37lFqZaGUcMeiaAQbN2sw9WJ3US1oeS6JLtZOLg4mXXGK2fWYsRxxUqBl0zV~NSeIjeyyjktzXPbzBiwh1BEJAHtXCWV0GEU463mMcRm2tp4BFqi7giY2g__"
            ));
        }
    }

    @Builder
    public Diary(ViewType viewType, String contents, List<String> imgUrls) {
        this.viewType = viewType;
        this.contents = (contents != null && !contents.isEmpty()) ? contents : "베볼리"; // 기본값 적용
        this.imgUrls = (imgUrls != null && !imgUrls.isEmpty()) ? imgUrls : new ArrayList<>(List.of(
                "https://s3-alpha-sig.figma.com/img/8ee5/0c2e/b058dc79ca1625d68efd4664511165e3?Expires=1740960000&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=h~LN0GeB2EHXqKc7rGSxUDJvgZz~DZ2m0F6ET-OgqXREZXTRqRgYgvKDzjX1WxSeptpv0sNn9zNKqVJyDP4m2MT4VAIDZJb7Tzg57u0LGC~3Q3H3U3hLOP7mTYDCeCdPlJRV~1W2K8oYuIB68T3t-qZ0s6-6H5QGWmIqMjWDErIPWSZZdsjRU-htyx3jc49uzaPnaxZkr2YV-8hQJet8h-TI9usgTrWF37lFqZaGUcMeiaAQbN2sw9WJ3US1oeS6JLtZOLg4mXXGK2fWYsRxxUqBl0zV~NSeIjeyyjktzXPbzBiwh1BEJAHtXCWV0GEU463mMcRm2tp4BFqi7giY2g__"
        ));
    }

    @OneToOne
    @JoinColumn(name = "gameId", unique = true)
    private GameInfo gameInfo;

    @ManyToOne
    @JoinColumn(name = "certificateId", nullable = false)
    private User user;

    public void update(LocalDate date, ViewType viewType, String contents, List<String> imgUrls, GameInfo gameInfo) {
        this.date = date;
        this.viewType = viewType;
        this.contents = (contents != null && !contents.isEmpty()) ? contents : "베볼리"; // 기본값 적용
        this.imgUrls = (imgUrls != null && !imgUrls.isEmpty()) ? imgUrls : new ArrayList<>(List.of(
                "https://s3-alpha-sig.figma.com/img/8ee5/0c2e/b058dc79ca1625d68efd4664511165e3?Expires=1740960000&Key-Pair-Id=APKAQ4GOSFWCW27IBOMQ&Signature=h~LN0GeB2EHXqKc7rGSxUDJvgZz~DZ2m0F6ET-OgqXREZXTRqRgYgvKDzjX1WxSeptpv0sNn9zNKqVJyDP4m2MT4VAIDZJb7Tzg57u0LGC~3Q3H3U3hLOP7mTYDCeCdPlJRV~1W2K8oYuIB68T3t-qZ0s6-6H5QGWmIqMjWDErIPWSZZdsjRU-htyx3jc49uzaPnaxZkr2YV-8hQJet8h-TI9usgTrWF37lFqZaGUcMeiaAQbN2sw9WJ3US1oeS6JLtZOLg4mXXGK2fWYsRxxUqBl0zV~NSeIjeyyjktzXPbzBiwh1BEJAHtXCWV0GEU463mMcRm2tp4BFqi7giY2g__"
        ));
        this.gameInfo = gameInfo;
    }
}
