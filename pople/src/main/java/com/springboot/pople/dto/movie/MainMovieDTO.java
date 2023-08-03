package com.springboot.pople.dto.movie;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MainMovieDTO {

    private Long id;
    private String movieName;  //  양화 제목
    private String imgUrl; // 이미지 경로
    private String movieSummary; // 영화 줄거리
    private String movieTime; // 영화 러닝 타임
    private String moveiRating; // 영화 관람 등급
    private String movieDate;  //   영화 개봉일

    @QueryProjection
    public MainMovieDTO(Long movieid, String movieName,String movieSummary,String imgUrl,
                        String movieTime,String moveiRating,String movieDate){
        this.id = movieid;
        this.movieName = movieName;
        this.movieSummary = movieSummary;
        this.imgUrl = imgUrl;
        this.movieTime = movieTime;
        this.moveiRating = moveiRating;
        this.movieDate = movieDate;
    }



}
