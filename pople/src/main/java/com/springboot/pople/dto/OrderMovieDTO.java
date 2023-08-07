package com.springboot.pople.dto;


import com.springboot.pople.entity.OrderMovie;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class OrderMovieDTO {
    private String itemNm; // 주문 상품이름
    private int count; // 주문 수량
    private int orderPrice; //  주문 금액
    private String imgUrl; // 주문 상품 이미지 경로

    // 생성자로 통해 orderItem객체와 이미지 경로 설정
    // 주문 상품정보 저장한 DTO: 조회한 주문 데이터를 화면에 보낼 때 사용할 DTO
    public OrderMovieDTO(OrderMovie ordermovie, String imgUrl){
        this.itemNm = ordermovie.getMovie().getMovieName();
        this.count = ordermovie.getCount();
        this.orderPrice = ordermovie.getOrderPrice();
        this.imgUrl = imgUrl;
    }
}
