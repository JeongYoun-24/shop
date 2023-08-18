package com.springboot.pople.dto;



import com.springboot.pople.constant.MovieStatus;
import com.springboot.pople.dto.movie.MovieImgDTO;
import com.springboot.pople.entity.Cinema;
import com.springboot.pople.entity.Movie;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

    private Long movieid;  // 영화 번호
    private String movieName;  //  양화 제목
    private String  moviePoster;  // 영화 이미지 (포스터)
    private String movieSummary; // 영화 줄거리
    private String movieTime; // 영화 러닝 타임
    private String movieRating; // 영화 관람 등급
    private String movieDate;  //   영화 개봉일

    private MovieStatus movieStatus;  // 영화 게시 여부
    private String imgUrl;


    @Builder.Default
    private List<MovieImgDTO> imgDTOList = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper();
    public static MovieDTO of(Movie movie){
        // entity -> dto
        return modelMapper.map(movie, MovieDTO.class);
    }



}
