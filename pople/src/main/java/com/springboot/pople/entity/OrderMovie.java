package com.springboot.pople.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Log4j2
public class OrderMovie {

    @Id
    @GeneratedValue
    @Column(name = "order_movie_id")
    private Long id;

    // 하나의 상품은 여러 주문상품으로 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    // 한 번의 주문에 여러 개의 상품을 주문할 수 있는 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="order_id")
    private Order order;


    private int orderPrice;
    private int count;

    public static OrderMovie createOrderItem(Movie movie, int count,int price){
        OrderMovie orderItem = new OrderMovie();
        log.info("총가격 나와라"+price);
        
        orderItem.setMovie(movie);// 주문 상품 설정
        orderItem.setCount(count);// 주문 수량 설정
        orderItem.setOrderPrice(price);
//        orderItem.setOrderPrice(movie.getPrice());// 주문 가격 설정
        log.info("총가격 나와라22"+orderItem);
//        item.removeStock(count); // 주문시 재고 수량 감소

        return orderItem;
    }
    public int getTotalPrice(){
        return orderPrice*count;
    }

    // 주문 취소시 예매 수량만큼 좌석 원상복귀 해주는 메서드
  //  public void cancel() {
  //      this.getMovie().addStock(count);
    // }

}
