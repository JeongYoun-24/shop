package com.springboot.pople.service.order;

import com.springboot.pople.dto.*;
import com.springboot.pople.dto.movieschedule.MovieScheduleDTO;
import com.springboot.pople.entity.*;
import com.springboot.pople.repository.OrderRepository;
import com.springboot.pople.repository.OrderRepository2;
import com.springboot.pople.repository.UsersRepository;
import com.springboot.pople.repository.item.ItemImgRepository;
import com.springboot.pople.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository; // 상품 정보 로직 처리
    //private final ItemImgRepository itemImgRepository; // 상품 이미지 처리 로직
    private final UsersRepository usersRepository;// 회원 로직 처리
    private final OrderRepository2 orderRepository;// 주문서 로직 처리
    private final ItemImgRepository itemImgRepository;



    public OrderDTO readOne (Long orderId){ // 조회
        Optional<Order2> order =orderRepository.findById(orderId);

        OrderDTO scheduleDTO = modelMapper.map(order, OrderDTO.class);

        return scheduleDTO;
    }

    // 상품 주문 서비스
    public Long order(OrderDTO2 orderDTO, String name){

        // 주문 상품에 대한 기본 정보 조회
        Item item = itemRepository.findById(orderDTO.getItemId())
                .orElseThrow(EntityNotFoundException::new);

        // 현재 로그인한 회원의 이메일(아이디) 정보를 이용해서 회원 정보조회
        Users users = usersRepository.findByName(name);

        // 주문 상품목록을 저장할 객체 생성
        List<OrderItem> orderItemList = new ArrayList<>();

        // 주문할 상품 엔티티와 주문 수량을 이용하여 주문 상품 엔티티를 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDTO.getCount());

        // 생성된 주문 상품 엔티티를 리스트에 보관
        orderItemList.add(orderItem);
        // 회원정보, 주문할 상품 리스트 정보를 가지고 주문 엔티티 생성
        Order2 order = Order2.createOrder2(users, orderItemList);
        // 생성된 주문 엔티티를 저장
        orderRepository.save(order);

        return order.getId();

    }

    // 주문 목록 서비스
    @Transactional(readOnly = true)
    public Page<OrderHistDTO2> getOrderList(String name, Pageable pageable){

        Users users = usersRepository.findByName(name);

        // 1. 사용자의 아이디(email)와 페이징 조건을 이용해 주문 목록 조회 요청
        List<Order2> orders = orderRepository.findOrders(name, pageable);
        log.info("11111"+orders);

        // 2. 사용자의 총 주문 개수
        Long totalCount = orderRepository.countOreder(name);
        log.info("222222"+totalCount);
        // 3. 검색하여 가져온 주문 목록을 순회하여 구매이력 페이지에 전달할 DTO생성
        List<OrderHistDTO2> orderHistDTOS = new ArrayList<>();

        for(Order2 order : orders){
            OrderHistDTO2 orderHistDTO = new OrderHistDTO2(order);// 주문내역

            List<OrderItem> orderItems = order.getOrderItem2();// 주문상품


            for(OrderItem orderItem: orderItems){
                // 주문 상품 대표 이미지 조회: 주문서에 있는 상품entity로 통행 상품id추출하여 상품대표상품이미지 조회
                ItemImg itemImg = itemImgRepository
                        .findByItemIdAndRepImgYn((orderItem.getItem().getId()), "Y" );

                // 주문 상품 정보(수량, 개수...), 상품 이미지
                OrderItemDTO orderItemDTO = new OrderItemDTO(orderItem, itemImg.getImgUrl());

                // 주문이력 리스트에 주문상품 추가
                orderHistDTO.addOrderitemDTO2(orderItemDTO);

                log.info("==> 주문 이력 상품 목록");
                log.info(orderItem.getItem().toString());
                log.info(itemImg.getImgUrl().toString());
                log.info(orderItemDTO.toString());
                log.info("===");

            }// end for (orderItems : 주문서에 있는 주문상품목록)

            orderHistDTOS.add(orderHistDTO); // 주문이력 List객체에 주문이력 상품 추가

            log.info("==> 고객이 주문한 주문서목록 (1:N)");
            log.info(orderHistDTOS.toString());
            log.info("===");

        } // end for (orders: 주문목록)


        return new PageImpl<>(orderHistDTOS, pageable, totalCount);
    }

    // 로그인한 사용자와 주문 데이터를 생성한 사용자가 같은지 검사, 같은 경우 true반환
    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String name){
        Users ckusers = usersRepository.findByName(name);

        Order2 order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        // 현재 로그인 사용자와 상품주문한 사용자가 동일한지 검사
        Users saveMember = order.getUsers();
        if (!StringUtils.equals(ckusers.getUserid(), saveMember.getUserid())){
            return false;// 일치하지 않으면 false
        }

        return true;
    }

    // 주문 취소 상태로 변경하면 변경 감지 기능에 의해서 트랜잭션이 끝날 대 update쿼리가 실행
    public void cancelOrder(Long orderId){
        Order2 order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        order.cancelOrder2();
    }





}
