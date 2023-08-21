package com.springboot.pople.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter@Setter@ToString
public class OrderItem  {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    // 하나의 상품은 여러 주문상품으로 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // 한 번의 주문에 여러 개의 상품을 주문할 수 있는 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="order_id2")
    private Order2 order;


    private int orderPrice;
    private int count;
    private LocalDateTime regDate;

//    private LocalDateTime regTime;
//    private LocalDateTime updateTime;

    // 주문 상품정보, 수량
    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();

        orderItem.setItem(item);// 주문 상품 설정
        orderItem.setCount(count);// 주문 수량 설정
        orderItem.setOrderPrice(item.getPrice());// 주문 가격 설정

        item.removeStock(count); // 주문시 재고 수량 감소

        return orderItem;
    }

    public int getTotalPrice(){
        return orderPrice*count;
    }

    // 주문 취소시 주문 수량만큼 상품 재고를 더 해주는 메서드
    public void cancel() {
        this.getItem().addStock(count);
    }


}

/*

- 연관 관계의 주인(주체)은 외래키가 있는 곳으로 설정
- 연관 관계의 주인(주체)이 외래키를 관리(등록,수정,삭제)
- 주인(주체)이 아닌 쪽은 연관 관계 매핑 시 mappedBy 속성의 값으로 연관관계의 주인(주체)를 설정
- 주인(주체)이 아닌 쪽은 읽기만 가능




 */
