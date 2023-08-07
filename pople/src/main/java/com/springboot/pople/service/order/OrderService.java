package com.springboot.pople.service.order;

import com.springboot.pople.dto.OrderDTO;
import com.springboot.pople.dto.movieschedule.MovieScheduleDTO;
import com.springboot.pople.entity.MovieSchedule;
import com.springboot.pople.entity.Order;
import com.springboot.pople.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    public OrderDTO readOne (Long orderId){ // 조회
        Optional<Order> order =orderRepository.findById(orderId);

        OrderDTO scheduleDTO = modelMapper.map(order, OrderDTO.class);

        return scheduleDTO;
    }




}
