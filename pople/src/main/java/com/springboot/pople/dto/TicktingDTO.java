package com.springboot.pople.dto;

import com.springboot.pople.dto.movie.MovieFormDTO;
import com.springboot.pople.dto.movie.MovieImgDTO;
import com.springboot.pople.entity.*;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
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
    private String userid;
    private Long scheduleid;

    private Long movieCode;  // 영화 번호
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
    private String movieRating; // 관람 등급



    // 주문 상품 정보를 저장할 List객체 생성
    private List<OrderMovieDTO> orderItemDTOList = new ArrayList<>();
    public void addOrderMovieDTO(OrderMovieDTO orderMovieDTO){
        // 주문 상품 리스트 객체에  주문 상품 정보 추가
        orderItemDTOList.add(orderMovieDTO);
    }


//    public TicktingDTO(Tickting tickting){
//        this.id = tickting.getId();
//        this.regDate = tickting.getRegDate()
//                .format(DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:mm")));
//        this.userid = tickting.getUsers();
//    }


}







