package com.springboot.pople.dto;

import com.springboot.pople.constant.OrderStatus;
import com.springboot.pople.entity.Order;
import com.springboot.pople.entity.Order2;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// 주문 정보를 담을 DTO
@Getter@Setter@ToString
public class OrderHistDTO2 {
    private Long orderId; // 주문 아이디
    private String orderDate;// 주문 날짜
    private OrderStatus orderStatus;// 주문상태(주문,취소)

    // 생성자를 통해  entity -> dto 전달
    public OrderHistDTO2(Order2 order){
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate()
                .format(DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:mm")));
        this.orderStatus = order.getOrderStatus();
    }


    /* 상품*/
    // 주문 상품 정보를 저장할 List객체 생성
    private List<OrderItemDTO> orderItemDTOList2 = new ArrayList<>();
    public void addOrderitemDTO2(OrderItemDTO orderItemDTO){
        // 주문 상품 리스트 객체에  주문 상품 정보 추가
        orderItemDTOList2.add(orderItemDTO);
    }





}
