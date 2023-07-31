package com.springboot.pople.dto;

import com.springboot.pople.entity.Movie;
import com.springboot.pople.entity.Users;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {


    private Long id;

    private Long movieid;

    private String userid;

    private String content; // 댓글 내용
    private LocalDateTime regDate; // 작성 날짜
    private int star; //별점



}
