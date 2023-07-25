package com.springstudy.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class OrderItem extends BasicEntity{
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    // 하나의 상품은 여러 주문상품 으로 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    // 한 번의 주문에 여러개의 상품을 주문 할수 있는 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;


    private int orderPrice;
    private int count;





//    private LocalDateTime regTime;
//    private LocalDateTime updateTime;

}
/*
연관 관계의 주인 (주체)은 외래키가 있는 곳으로 설정
연관 관계의 주인 (주체)이 외래키를 관리 (등록,수정,삭제)
주인(주체)이 아닌 쪽은 연관 관계 매핑 시 mappedBy 속성의 값으로 연관 관계의 주인(주체) 주인(주체)이 아닌쪽은 읽기만 가능




 */


