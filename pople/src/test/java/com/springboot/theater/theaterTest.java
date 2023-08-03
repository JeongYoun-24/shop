package com.springboot.theater;

import com.springboot.pople.PopleApplication;
import com.springboot.pople.dto.TheaterDTO;
import com.springboot.pople.entity.Cinema;
import com.springboot.pople.entity.Theater;
import com.springboot.pople.repository.TheaterRepository;
import com.springboot.pople.service.theater.TheaterService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@Log4j2
@SpringBootTest(classes = PopleApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class theaterTest {

    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private TheaterService theaterService;
    @Autowired
    private ModelMapper modelMapper;

    @Test
    @DisplayName(value = "상영관 등록 테스트 ")
    public void theaterTest(){

//        Cinema cinema =new Cinema();
    Long cinema = 1L;

        TheaterDTO theaterDTO = TheaterDTO.builder()
                .id(1L)
                .cinemaid(cinema)
                .build();

        log.info(theaterDTO);

      Theater theater =  modelMapper.map(theaterDTO,Theater.class);

      log.info(theater);


//        theaterService.register(theaterDTO);

    }



}
