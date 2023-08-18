package com.springboot.pople.entity;

import com.springboot.pople.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders2")
@Getter
@Setter
@ToString(exclude = "orderMovies" )
public class Order2 {

    @Id
    @GeneratedValue
    @Column(name ="order_id2")
    private Long id;

    // 한 명의 회원은 여러 번 주문을 할 수 있는 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    private LocalDateTime orderDate; // 주문일
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;// 주문상태

    // 하나의 주문이 여러 개의 주문 상품을 갖는 List자료형으로 맵핑
    // Order엔티티가 주인(주체)이 아니므로 "mappedby"속성으로 연관관계의 주인을 설정
    // orderItems에 있는 Order에 의해 관리

    @OneToMany(
            fetch = FetchType.LAZY,
//              mappedBy = "/templates/order",
            orphanRemoval = true, // 고아객체 제거
            cascade = CascadeType.ALL) // 고객이 주문할 상품을 선택하고 주문할 때 주문 엔티티를 저장하면서 주문 상품 엔티티도 함께 저장되는 경우
    private List<OrderItem> orderItem2 = new ArrayList<>();

    public void addOrderItem2(OrderItem orderItem){
        orderItem2.add(orderItem);
        // 양방향 참조 주문 상품객체에 주문 객체을 설정
        orderItem.setOrder(this);
    }


    // 2. 주문서 주문내역 구성 :  주문 상품목록, 주문 고객, 주문상태
    public static Order2 createOrder2(
            Users users, List<OrderItem> orderItemList){

        Order2 order = new Order2();

        order.setUsers(users);// 주문고객
        for(OrderItem orderItem : orderItemList){//주문상품
            order.addOrderItem2(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER);// 주문상태
        order.setOrderDate(LocalDateTime.now());// 주문일자

        return order;

    }

    // 주문한 상품 총 금액 계산 처리
    public int getTotalPrice2(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItem2){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }


    // 주문 상품 취소시 상품목록에서 주문상품 메서드 호출
    public void cancelOrder2(){
        this.orderStatus = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItem2){
            orderItem.cancel();
        }
    }



}
