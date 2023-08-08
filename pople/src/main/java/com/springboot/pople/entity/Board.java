package com.springboot.pople.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;  // 제목

    @Column(length = 2000, nullable = false)
    private String content; // 내용

    private String boardImg; // 이미지

    @Column(length = 50, nullable = false)
    private String writer; // 작성자

    private int hitcount; // 조회수

    private LocalDateTime regDate;

    // update(변경) 메서드 정의
    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }




}
