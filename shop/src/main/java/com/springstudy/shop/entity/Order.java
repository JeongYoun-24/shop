package com.springstudy.shop.entity;

import com.springstudy.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BasicEntity { // 주문 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id ;
    // 한명의 화원은 여러번 주문을 할수 있는 관계
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private LocalDateTime orderDate; //주문 날짜
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태
    
    // 하나의 주문이 여러개의 주문 상품을 갖는 list 자료형으로 맵핑
    // order엔티티가 주인(주체)이 아니므로 "mappedBy" 속성으로 연관 관계의 주인을 설정
    // orderItems에 잇는 Order에 의해 관리
    @OneToMany(mappedBy = "order",

            cascade = CascadeType.ALL)
    private List<OrderItem> orderItems  =new ArrayList<>(); // 주문 정보

//    private LocalDateTime regTime;
//    private LocalDateTime updateTime;

}
/*
  cascade = CascadeType.ALL)
       -영속성전이 : 부모 엔티티의 영속성 상태 변화를 자식 엔티티 모두 전이하는 옵션
       -부모엔티티 저장되면 , 자식 엔티티 자동 저장
       -고객이 주문할 상품을 선택하고 주문 할때 주문 엔티티를 저장하면서
       -주문 상품 엔티티도 함께 저장되는 경우

-- CASCADE 종류
-- PERSIST, MERGE , REMOVE,REFRESH,DETACH , ALL

Orders

order_id
member_id
order_date
order_status



*/