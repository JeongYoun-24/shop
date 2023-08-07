package com.springboot.movei;

import com.springboot.pople.PopleApplication;
import com.springboot.pople.dto.MovieDTO;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.repository.movie.MovieRepository;

import com.springboot.pople.service.movie.MovieService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


import java.util.List;
import java.util.Optional;


@Log4j2
@SpringBootTest(classes = PopleApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class MovieTest {


    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieService movieService;


    @Test
    @DisplayName("Movie 영화 insert테스트")
    public void testInsert222() {
//        IntStream.rangeClosed(3, 5).forEach(i -> {
        // 객체 생성
        Movie movie = Movie.builder()
//                    .movieno(2L)
                .movieName("범죄도시3" )
                .moviePoster("jpg")
                .movieSummary("이때까지 이런 영화는 없었다 ㅇㅈ?")
                .movieTime("188분")
                .movieRating("15세이상")
                .movieDate("7/30")
//                .movieStatus(true)
                .build();

        log.info(movie);

        // 영속성 컨텍스트에 반영
        Movie result = movieRepository.save(movie);

//        });

    }
    @Test
    @DisplayName("Movie 영화 insert테스트(Service)")
    public void testInsert() {
//        IntStream.rangeClosed(3, 5).forEach(i -> {
        // 객체 생성
        MovieDTO movie = MovieDTO.builder()
//                    .movieno(2L)
                .movieName("범죄도시3" )
                .moviePoster("jpg")
                .movieSummary("이때까지 이런 영화는 없었다 ㅇㅈ?")
                .movieTime("188분")
                .moveiRating("15세이상")
                .movieDate("7/30")
//                .movieStatus(true)
                .build();

        log.info(movie);

        // 영속성 컨텍스트에 반영
         movieService.register(movie);

//        });

    }






    @Test
    @DisplayName("Movie 영화 조회 테스트 ")
    public void testselect(){

        List<Movie> movieList = movieRepository.findAll();
        movieList.forEach(item -> {
            log.info(item);
        });

    }

    @Test
    @DisplayName("Movie 영화 수정  테스트 ")
    public void testupdate(){

        Long movie_code = 1L;
        Optional<Movie> item_result = movieRepository.findById(movie_code);
        Movie movie = item_result.orElseThrow();

        movie.change("영화 이름 수정 ", "영화 이미지 수정","영화 줄거리 수정 ");
        movieRepository.save(movie);

        List<Movie> itemList = movieRepository.findAll();
        itemList.forEach(vo ->{
            log.info(vo);
        });

    }


    @Test
    @DisplayName("Movie 영화 삭제 테스트 ")
    public void testdelete(){
        Long movie_code = 5L;
        movieRepository.deleteById(movie_code);

    }

    @Test
    @DisplayName("영화 조회")
    public void testFind(){
        Long movieid = 1L;
        MovieDTO movieDTO =   movieService.readOne(movieid);
        log.info(movieDTO);

    }

    @Test
    @DisplayName("영화 조회")
    public void testFindss(){
        String movieName = "범죄도시3";
//     Movie movie =  movieRepository.findByMovieName(movieName);
//        log.info(movie);
        MovieDTO movieDTO =   movieService.nameOne(movieName);
        log.info(movieDTO);

    }


}
