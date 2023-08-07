package com.springboot.pople.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
@Entity
@Getter
@Setter
@ToString
public class TicktingOrder {

    @Id
    @GeneratedValue
    @Column(name = "tick_movie_id")
    private Long id;

    // 하나의 티켓은 여러 영화 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    // 한 번의 주문에 여러 개의 티켓을 주문할 수 있는 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tickting_id")
    private Tickting tickting;


    private int orderPrice;
    private int count;

    public static OrderMovie createOrderItem(Movie movie, int count,int price){
        OrderMovie orderItem = new OrderMovie();

        orderItem.setMovie(movie);// 주문 상품 설정
        orderItem.setCount(count);// 주문 수량 설정
        orderItem.setOrderPrice(price);
        orderItem.setOrderPrice(movie.getPrice());// 주문 가격 설정

//        item.removeStock(count); // 주문시 재고 수량 감소

        return orderItem;
    }
    public int getTotalPrice(){
        return orderPrice*count;
    }


}
