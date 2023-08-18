package com.springboot.pople.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="cart")
@Getter
@Setter
@ToString
public class Cart {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 엔티티안에 다른 엔티티가 있을경우 연관관계 맵핑을 설정해야함.
    // 장바구니가 회원을 참조하는 형태 : 장바구니(외래키), 회원(참조:기본키)
    // 생략시(fetch = FetchType.EAGER):즉시로딩, (fetch = FetchType.LAZY):지연: 필요할 때 연결
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // 외래키로 설정됨.
    private Users users;

    // 회원 장바구니 생성 : 회원 1명당 1개의 장바구니 맵핑
    public static Cart createCart(Users users){
        Cart cart =new Cart();
        cart.setUsers(users);

        return cart;
    }



}
