package com.springboot.order;

import com.springboot.pople.PopleApplication;
import com.springboot.pople.dto.OrderDTO;
import com.springboot.pople.entity.Order;
import com.springboot.pople.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;
@Log4j2
@SpringBootTest(classes = PopleApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class OrderTest {

    @Autowired
    private  OrderRepository orderRepository;
    @Autowired
    private  ModelMapper modelMapper;
    @Test
    @DisplayName(value = "상품 조회")
    public void readOne (){ // 조회
        Long orderId = 46L;
        String name = "bbb";
        Order order =orderRepository.userId(orderId);
        log.info(order);
        OrderDTO scheduleDTO = modelMapper.map(order, OrderDTO.class);
        log.info(scheduleDTO);

    }


}
