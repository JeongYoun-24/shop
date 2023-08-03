package com.springboot.pople.dto.movie;

import com.springboot.pople.constant.MovieStatus;
import com.springboot.pople.entity.Movie;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class MovieFormDTO {

    private Long id;
    @NotBlank(message = "영화이름은 필수 입력 값입니다.")
    private String movieName;  //  양화 제목
//        @NotNull(message = "상품명은 필수 입력 값입니다.")
    private String  moviePoster;  // 영화 이미지 (포스터)
    @NotNull(message = "영화줄거리는 필수 입력 값입니다.")
    private String movieSummary; // 영화 줄거리
    @NotNull(message = "영화러닝시간은 필수 입력 값입니다.")
    private String movieTime; // 영화 러닝 타임
    @NotNull(message = "영화관람등급 은 필수 입력 값입니다.")
    private String movieRating; // 영화 관람 등급
    @NotNull(message = "영화개봉일 은 필수 입력 값입니다.")
    private String movieDate;  //   영화 개봉일

    private MovieStatus movieStatus;  // 영화 게시 여부

    // 상품 저장 후 수정할 때 상품 이미지 정보를 저장하는 리스트
    private List<MovieImgDTO> movieImgDTOList = new ArrayList<>();
    // 상품의 이미지 아이디를 저장하는 리스트
    private List<Long> movieImgIds = new ArrayList<>();

    // entity -> DTO
    private static ModelMapper modelMapper = new ModelMapper();

    public static MovieFormDTO of(Movie movie) {
        return modelMapper.map(movie, MovieFormDTO.class);
    }

    // DTO -> entity
    public Movie createMovie( ) {

        return modelMapper.map(this, Movie.class);
    }

}




