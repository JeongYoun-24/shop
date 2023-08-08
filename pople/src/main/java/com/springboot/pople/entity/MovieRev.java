package com.springboot.pople.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MovieRev")
public class MovieRev { // 영화 리뷰

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "rev_id")
    private Long revid;  // 리뷰 번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;   // 회원 아이디
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;  // 영화 번호

    @Column(nullable = false,length = 500)
    private String revTitle; // 제목
    @Column(nullable = false,length = 1000)
    private String revContent;  // 내용
    private int star;
    private LocalDateTime revDate;

    public void change(String rev_title,String rev_content){
        this.revTitle = revTitle;
        this.revContent = revContent;

    }


}
