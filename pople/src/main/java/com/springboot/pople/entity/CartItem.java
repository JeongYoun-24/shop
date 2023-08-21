package com.springboot.pople.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    private int count;
    private LocalDateTime regDate;

    // 추가
    // 1. 장바구니에 담을 상품 엔티티
    public static CartItem createCartItem(Cart cart,Item item ,int count){
        CartItem cartItem = new CartItem();

        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);
        cartItem.setRegDate(LocalDateTime.now());

        return cartItem;
    }
    // 2. 장바구니에 담을 상품 수량 증감 처리
    public void addCount(int count){
        this.count += count;
    }

    // 3. 장바구니 뷰에서 수량의 변화가 생길 경우 상품 수량 처리
    public void updateCount(int count){this.count = count;}



}
