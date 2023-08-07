package com.springboot.pople.dto;

import com.springboot.pople.constant.OrderStatus;
import com.springboot.pople.constant.TicktingStatus;
import com.springboot.pople.entity.Order;
import com.springboot.pople.entity.Tickting;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicktingHistDTO {
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
    private String regDate;  // 결제 날짜
    private TicktingStatus ticktingStatus;
    private String movieRating; // 관람등급


    public TicktingHistDTO(Tickting tickting){
        this.id = tickting.getId();
        this.regDate = tickting.getRegDate()
                .format(DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:mm")));
        this.ticktingStatus = tickting.getTicktingStatus();
        this.theaterCode = tickting.getTheaterCode();
        this.movieCode = tickting.getMovieCode();
        this.movieName = tickting.getMovieName();
        this.movieTime = tickting.getMovieTime();
        this.ticktingPrice = tickting.getTicktingPrice();
        this.count = tickting.getCount();
        this.cinemaName = tickting.getCinemaName();
        this.movieRating = tickting.getMovieRating();

    }

    // 주문 상품 정보를 저장할 List객체 생성
    private List<TicktingOrderDTO> orderItemDTOList = new ArrayList<>();
    public void addMovieDTO(TicktingOrderDTO orderMovieDTO){
        // 주문 상품 리스트 객체에  주문 상품 정보 추가
        orderItemDTOList.add(orderMovieDTO);
    }
}

