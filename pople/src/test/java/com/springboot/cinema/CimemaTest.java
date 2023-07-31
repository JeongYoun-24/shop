package com.springboot.cinema;

import com.springboot.pople.PopleApplication;
import com.springboot.pople.entity.Cinema;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.repository.CinemaRepository;
import com.springboot.pople.repository.MovieRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.Column;
import java.util.List;
import java.util.Optional;

@Log4j2
@SpringBootTest(classes = PopleApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class CimemaTest {


    @Autowired
    private CinemaRepository cinemaRepository;


    @Test
    @DisplayName("Cinema 영화 insert테스트")
    public void testInsert222() {
//        IntStream.rangeClosed(3, 5).forEach(i -> {
//             객체 생성
        Cinema cinema = Cinema.builder()
                    .cinemaid(2L)
                    .cinemaName("CGV" )
                    .cinemaAddrss("경상남도 양산시 증산역앞 CGV ")
                    .cinemaSeatCount(72)

                    .build();



            // 영속성 컨텍스트에 반영
        Cinema result = cinemaRepository.save(cinema);

//        });

    }


    @Test
    @DisplayName("Cinema 영화 조회 테스트 ")
    public void testselect(){

        List<Cinema> movieList = cinemaRepository.findAll();
        movieList.forEach(item -> {
            log.info(item);
        });

    }

    @Test
    @DisplayName("Cinema 영화 수정  테스트 ")
    public void testupdate(){

        Long movie_code = 1L;
        Optional<Cinema> cinema = cinemaRepository.findById(movie_code);
        Cinema movie = cinema.orElseThrow();

        movie.change("영화 이름 수정 ", "영화 이미지 수정",48);
        cinemaRepository.save(movie);

        List<Cinema> movieList = cinemaRepository.findAll();
        movieList.forEach(vo ->{
            log.info(vo);
        });

    }


    @Test
    @DisplayName("Cinema 영화 삭제 테스트 ")
    public void testdelete(){
        Long movie_code = 5L;
        cinemaRepository.deleteById(movie_code);

    }





}
