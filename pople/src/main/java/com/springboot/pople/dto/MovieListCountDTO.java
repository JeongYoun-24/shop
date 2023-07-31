package com.springboot.pople.dto;

import lombok.Data;

@Data
public class MovieListCountDTO {

    private Long movie_code;  // 영화 번호
    private String movie_name;  //  양화 제목
    private String  movie_poster;  // 영화 이미지 (포스터)
    private String movie_summary; // 영화 줄거리
    private String movie_time; // 영화 러닝 타임
    private String movei_Rating; // 영화 관람 등급
    private String movie_date;  //   영화 개봉일
    private Boolean movie_status;  // 영화 게시 여부

    private  Long revCount;
}
