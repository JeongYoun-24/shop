package com.springboot.pople.dto;

import com.springboot.pople.entity.Movie;

import com.springboot.pople.entity.MovieSchedule;
import com.springboot.pople.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicktingDTO {

//    private Long ticktingCode;  //영화 내역 번호
//    private Long ticktingCount;  // 예약 인원수
//    private String seatNumber; // 예약좌석 번호
//    private LocalDateTime ticktingDate;
//    private Long movieCode;  // 영화 번호
//    private String userId; // 회원 아이디
//    private Long scheduleId;  // 영화 상영 시간 번호

    private Long id;  //영화 내역 번호
    private Users users;
    private Long scheduleid;

    private long movieCode;  // 영화 번호
    private Long theaterCode;  // 상영관 번호
    private LocalDateTime dayDate; // 애매 날짜
    private String movieName; // 영화 이름
    private String movieTime; // 영화 런닝타임
    private String cinemaName; // 영화관 이름
    private String seat;       // 좌석
    private int count;       // 예매 인원수
    private int ticktingPrice; // 티켓 가격
    private String card;       // 카드사
    private String cardNum;   // 카드 번호
    private String phone;     // 핸드폰 번호
    private String cardCheck;   // 카드 결제 유무
    private LocalDateTime regDate;  // 결제 날짜

}







