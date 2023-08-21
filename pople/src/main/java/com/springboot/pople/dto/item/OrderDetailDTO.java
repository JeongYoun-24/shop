package com.springboot.pople.dto.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderDetailDTO { // 장바구니 조회 페이지에 전달할 DTO

    private Long id;    // 주문 상품 아이디
    private String itemName;      // 상품이름
    private int price;              // 상품 가격
    private String itemDetail;      // 상품 정보
    private int count;          // 상품 수량
    private String imgUrl;      // 상품 이미지 경로
    private LocalDateTime regDate;

    // 장바구니 페이지에 전달할 데이터를 생성자의 파라미터로 만들어준다
    public OrderDetailDTO(Long id, String itemName, String itemDetail , int price , int count, String imgUrl){
        this.id = id;
        this.itemName = itemName;
        this.price= price;
        this.itemDetail = itemDetail;
        this.count=count;
        this.imgUrl = imgUrl;
//        this.regDate = regDate;

    }
//    public OrderItemDTO(CartItem orderItem, String imgUrl){
//        this.cartItemId = orderItem.getItem().getItemName();
//        this.itemName =
//        this.price = orderItem.getOrderPrice();
//        this.imgUrl = imgUrl;
//        this.count = count;
//    }



    // 생성자를 통해  entity -> dto 전달
//    public CartDetailDTO(CartItem cartItem){
//        this.cartItemId = cartItem.getId();
//
//    }



    /* 상품*/
    // 주문 상품 정보를 저장할 List객체 생성
    private List<OrderDetailDTO> cartItemDTOList2 = new ArrayList<>();
    public void addCartitemDTO2(OrderDetailDTO orderItemDTO){
        // 주문 상품 리스트 객체에  주문 상품 정보 추가
        cartItemDTOList2.add(orderItemDTO);
    }



}
