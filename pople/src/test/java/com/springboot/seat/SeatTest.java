package com.springboot.seat;

import com.springboot.pople.PopleApplication;
import com.springboot.pople.dto.TheaterDTO;
import com.springboot.pople.entity.Cinema;
import com.springboot.pople.entity.Seat;
import com.springboot.pople.entity.Theater;
import com.springboot.pople.repository.SeatRepository;
import com.springboot.pople.repository.TheaterRepository;
import com.springboot.pople.service.seat.SeatService;
import com.springboot.pople.service.theater.TheaterService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.stream.IntStream;

@Log4j2
@SpringBootTest(classes = PopleApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class SeatTest {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SeatService seatService;


    @Test
    @DisplayName(value = "상영관 등록 테스트 ")
    public void theaterTest(){
        IntStream.rangeClosed(1,1).forEach(i-> {
            Long cinemaid = 1L;
            Long theaterid = 1L;

            Cinema cinema =new Cinema();


            Theater theater =new Theater();



            Seat seat = Seat.builder()
                    .seatNo(i)
                    .seatGroup(01)
                    .seatLineNo("A")
                    .cinema(cinema)
                    .theater(theater)

                    .build();
        seatRepository.save(seat);


        });

    }
//    private Long seatid;  // 좌석 코드
//    private int seatNo; //좌석 번호
//    private int seatGroup; // 좌석 그룹
//    private String seatLineNo; // 좌석 라인줄 번호



}
