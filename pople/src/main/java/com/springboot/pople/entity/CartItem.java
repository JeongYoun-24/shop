package com.springboot.pople.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue
    @Column(name="cart_item_id")
    private Long id;

    // 연관관계 맵핑할 경우 : 참조하는 FK키를 기준으로 설정
    // 하나의 장부구니에는 여러개의 상품을 담을 수 있는 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;

    // 하나의 상품은 여러 장바구니의 장바구니 상품을 담을 수 있는 관계
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "item_id")
//    private Item item;
    private int count;


}
