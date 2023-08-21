package com.springboot.pople.repository.cart;

import com.springboot.pople.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // 현재 로그인 한 Cart조회
    Cart findByUsers_Userid(String userid);


}
