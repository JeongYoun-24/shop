package com.springboot.pople.dto;

import com.springboot.pople.entity.OrderMovie;
import com.springboot.pople.entity.Tickting;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TicktingOrderDTO {

    private String movieName; // 주문 상품이름
    private int count; // 주문 수량
    private int ticktingPrice; //  주문 금액
    private String imgUrl; //  영화 이미지 경로
    private String cinemaName;
    private Long movieCode;
    private String movieTime;





    // 생성자로 통해 orderItem객체와 이미지 경로 설정
    // 주문 상품정보 저장한 DTO: 조회한 주문 데이터를 화면에 보낼 때 사용할 DTO
    public TicktingOrderDTO(Tickting tickting, String imgUrl){
        this.movieName = tickting.getMovieName();
        this.count = tickting.getCount();
        this.ticktingPrice = tickting.getTicktingPrice();
        this.cinemaName = tickting.getCinemaName();
        this.movieCode = tickting.getMovieCode();
        this.movieTime = tickting.getMovieTime();
        this.imgUrl = imgUrl;
    }
}
