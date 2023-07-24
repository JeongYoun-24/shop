package com.springstudy.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue()
    @Column(name = "cart_item_id")
    private Long id;

    // 연관 관계 맵핑할 경우 : 함조하는 fk키를 기준으로 설정
    // 하나의 장바구니는 여러개의 삼품을 담을수 있는 관계
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // 하나의 상품은 여러 장바구니에 상품을 담을수 있는 관계
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    private int count;

}
/*
장바구니 아이템 엔티티 설계 : ( 장바구니정보 , 상품정보 , 맵핑괸계)


장바구니         1:N    장바구니와상품연결맵핑     N:1     상품
cart              ->       cart_item         <-     item
cart_id(pk)              cart_item(pk)              item_id(pk)
ember_id(fk)            cart_id(fk)                 item_nm()
                         item_id(fk)                price
                         count                      stock_number, item_....


cart_item
id      cart   item
1        A      A1
2        A      A2
3        B      A1
4        B      A2
5        C      A1



*/
