package com.springboot.pople.dto;

import com.springboot.pople.constant.OrderStatus;
import com.springboot.pople.entity.Users;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString(exclude = "orderMovies" )
public class OrderDTO {


    private Long id;

    private Users users;

    private LocalDateTime orderDate; // 주문일

    private OrderStatus orderStatus;// 주문상태





}
