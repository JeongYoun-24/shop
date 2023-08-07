package com.springboot.pople.service.movie;

import com.springboot.pople.dto.MovieDTO;
import com.springboot.pople.dto.MovieListCountDTO;
import com.springboot.pople.dto.PageRequestDTO;
import com.springboot.pople.dto.PageResponseDTO;

import com.springboot.pople.dto.movie.MovieFormDTO;
import com.springboot.pople.entity.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface MovieService {

    public Long register(MovieDTO movieDTO);
    public MovieDTO readOne(Long movieid);
    public void modify(MovieDTO movieDTO);
    public void remove(Long movieid);

    public MovieDTO nameOne(String movieName);
    public List<MovieDTO> AllList();


//    default Map<String,Object> dtoToEntity(MovieDTO movieDTO){
//        Map<String,Object> entityMap = new HashMap<>();
//        Movie movie  = Movie.builder()
//                .movieCode(movieDTO.getMovieCode())
//                .movieName(movieDTO.getMovieName())
//
//                .build();
//        entityMap.put("movie",movie);
//
//        List<MovieImgDTO> imgDTOList = movieDTO.getImgDTOList();
//        if(imgDTOList != null && imgDTOList.size() > 0){
//            List<MovieImgDTO> movieimgList =imgDTOList.stream().map(movieImgDTO->{
//                MovieImgDTO movieImg  = MovieImgDTO.builder()
//                        .path(movieImgDTO.getPath())
//                        .imgName(movieImgDTO.getImgName())
//                        .uuid(movieImgDTO.getUuid())
//                        .movie(movie)
//                        .build();
//            })
//
//        }
//
//    }


    // 클라이언트로 부터 요청한 페이지정보 처리하여 응답하는 메서드
//    PageResponseDTO<MovieDTO> list(PageRequestDTO pageRequestDTO);
//
//    // 댓글의 숫자까지 처리
//    PageResponseDTO<MovieListCountDTO> listReplyCount(PageRequestDTO pageRequestDTO);



}
