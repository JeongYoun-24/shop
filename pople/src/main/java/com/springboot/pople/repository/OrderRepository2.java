package com.springboot.pople.repository;

import com.springboot.pople.dto.item.CartDetailDTO;
import com.springboot.pople.entity.Cart;
import com.springboot.pople.entity.Order;
import com.springboot.pople.entity.Order2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

// 주문 이력 조회
// 조회 조건이 복잡하지 않을 경우 Query어노테이션 방식으로 구현
public interface OrderRepository2 extends JpaRepository<Order2, Long>, QuerydslPredicateExecutor<Order2> {


    List<Order2> findByUsers_Userid(String userid);

    // 현재 로그인 사용자의 주문 데이터를 페이징 조건에 맞춰서 조회
    @Query("select o from Order2 o where o.users.name = :name order by  o.orderStatus, o.orderDate desc")
    List<Order2> findOrders(@Param("name") String name, Pageable pageable);

//    // 현재 로그인한 회원의 주문 개수가 몇 개인지 조회
    @Query("select count(o) from Order2 o where o.users.name = :name")
    Long countOreder(@Param("name") String name);

    @Query("select o from Order2 o where o.id = :orderId")
    Order userId(@Param("orderId") Long orderId);

    //  ↑ 영화 관련
    //  ↓  상품 관련






}
