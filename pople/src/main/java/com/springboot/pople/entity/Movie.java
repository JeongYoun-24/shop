package com.springboot.pople.entity;

import com.springboot.pople.constant.MovieStatus;
import com.springboot.pople.dto.movie.MovieFormDTO;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "movieStatus")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Movie")
public class Movie {  // 영화

    @Id
    @Column(nullable = false,name = "movie_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieid;  // 영화 번호
    @Column(nullable = false,length = 100)
    private String movieName;  //  양화 제목
    @Column(length = 100)
    private String  moviePoster;  // 영화 이미지 (포스터)
    @Column(nullable = false,length = 1000)
    private String movieSummary; // 영화 줄거리
    @Column(nullable = false,length = 100)
    private String movieTime; // 영화 러닝 타임
    @Column(nullable = false,length = 100)
    private String movieRating; // 영화 관람 등급

    private String movieDate;  //   영화 개봉일

    @Enumerated(EnumType.STRING)
    private MovieStatus movieStatus;  // 영화 게시 여부


    public void change(String movie_name,String movie_poster,String movie_summary){
        this.movieName = movieName;
        this.moviePoster = moviePoster;
        this.movieSummary = movieSummary;

    }
    public void change2(String movie_date,Boolean movie_status){ //개봉일 및 게시여부 수정
        this.movieDate = movieDate;
        this.movieStatus = movieStatus;
    }
    public void change2(Boolean movie_status){ // 게시여부 수정
        this.movieStatus = movieStatus;
    } // 게시 여부 수정

    public void updateItem(MovieFormDTO movieFormDTO){
        this.movieName = movieFormDTO.getMovieName();
//        this.moviePoster = movieFormDTO.get;
        this.movieSummary = movieFormDTO.getMovieSummary();
        this.movieDate = movieFormDTO.getMovieDate();
        this.movieStatus = movieFormDTO.getMovieStatus();

    }



}









