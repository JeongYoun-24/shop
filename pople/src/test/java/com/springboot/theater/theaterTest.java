package com.springboot.theater;

import com.springboot.pople.PopleApplication;
import com.springboot.pople.dto.TheaterDTO;
import com.springboot.pople.dto.theater.TheaterFormDTO;
import com.springboot.pople.entity.Cinema;
import com.springboot.pople.entity.Theater;
import com.springboot.pople.repository.TheaterRepository;
import com.springboot.pople.service.theater.TheaterFormService;
import com.springboot.pople.service.theater.TheaterService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@Log4j2
@SpringBootTest(classes = PopleApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class theaterTest {

    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private TheaterService theaterService;

    @Autowired
    private TheaterFormService theaterFormService;
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

    @Test
    @DisplayName(value = "영화관 id값으로 조회 테스트 ")
    public void Test(){
        List<TheaterDTO>theaterDTOList= theaterService.theaterList(1L);
        log.info(theaterDTOList);

    }

    @Test
    @DisplayName(value = "영화관 id값으로 조회 테스트 ")
    public void Test2(){
        String theaterName = "1상영관";

        TheaterFormDTO  theaterDTOList= theaterFormService.theaterName(theaterName);
        log.info(theaterDTOList);

    }


}
