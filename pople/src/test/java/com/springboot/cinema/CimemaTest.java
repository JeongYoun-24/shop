package com.springboot.cinema;

import com.springboot.pople.PopleApplication;
import com.springboot.pople.entity.Cinema;
import com.springboot.pople.repository.CinemaRepository;
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
public class CimemaTest {


    @Autowired
    private CinemaRepository cinemaRepository;


    @Test
    @DisplayName("Cinema 영화 insert테스트")
    public void testInsert222() {
//        IntStream.rangeClosed(3, 5).forEach(i -> {
//             객체 생성
        Cinema cinema = Cinema.builder()
                .cinemaName("메가박스" )
                .cinemaAddrss("경상남도 양산시 강변로 440")
                .cinemaSeatCount(72)
                .xaxis("35.33826634341823")
                .yaxis("129.02693159659853 ")
                .build();

        Cinema result = cinemaRepository.save(cinema);

//        });

    }
//    Cinema cinema = Cinema.builder()
//            .cinemaName("롯데시네마 양산물금" )
//            .cinemaAddrss("경상남도 양산시 물금읍 백호로 68 ")
//            .cinemaSeatCount(72)
//            .xaxis("35.318002138539356")
//            .yaxis("129.00190453388709 ")
//            .build();
//      Cinema result = cinemaRepository.save(cinema);
//    Cinema cinema = Cinema.builder()

//            .cinemaName("메가박스" )
//            .cinemaAddrss("경상남도 양산시 물금읍 증산역로 177 ")
//            .cinemaSeatCount(72)
//            .xaxis("35.30989962941276")
//            .yaxis("129.0094416146745")
//            .build();
//
//    Cinema result = cinemaRepository.save(cinema);



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
