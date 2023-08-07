package com.springboot.pople.repository;

import com.springboot.pople.entity.Order;
import com.springboot.pople.entity.Tickting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicktingRepository extends JpaRepository<Tickting,Long>, QuerydslPredicateExecutor<Tickting> {


    @Query("select o from Tickting o where o.users.name = :name")
    List<Tickting> findTicktings(@Param("name") String Name, Pageable pageable);
    @Query("select o from Tickting o where o.users.name = :name")
    List<Tickting> findTicktingss(@Param("name") String Name);

//    @Query("select o from Order o where o.users.name = :name order by  o.orderStatus, o.orderDate desc")
//    List<Order> findOrders(@Param("name") String Name, Pageable pageable);

    @Query("select count(o) from Tickting o where o.users.name = :name")
    Long countTickting(@Param("name") String name);

}
