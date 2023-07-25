package com.springstudy.shop.repository;

import com.springstudy.shop.constant.ItemSellStatus;
import com.springstudy.shop.dto.MemberDTO;
import com.springstudy.shop.entity.Item;
import com.springstudy.shop.entity.Member;
import com.springstudy.shop.entity.Order;
import com.springstudy.shop.entity.OrderItem;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Log4j2
@Transactional // 테스트용 , lollback 자동 적용
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class OrderTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired(required = false)
    Member member;

    @Autowired
    OrderItemRepository orderItemRepository;

    @PersistenceContext
    EntityManager em;


    // 상품 생성
    @DisplayName("상품 저장 테스트")
    public Item createItem(){
        Item item = new Item();

        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트상품 상세설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

//        Item savedItem = itemRepository.save(item);
//        log.info(savedItem);

        return item;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest(){
        Order order = new Order();

        for(int i =0; i< 3; i++) {
            Item item = this.createItem();
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);

            order.getOrderItems().add(orderItem); //주문서 정보(주문서,주문상품,수량,금액)
        }

        orderRepository.saveAndFlush(order);  //엔티티로 저장함ㄴ서 강제로 flush 호출
        em.clear(); //DB에 있는 데이터 호출 하기 위해 영속성 컨텍스트 상태를 초기화

        Order saveOrder = orderRepository.findById(order.getId()).orElseThrow();

        Assertions.assertEquals(3,saveOrder.getOrderItems().size());


    }
    public Member createMember(){
        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setEmail("ccc@email.com");
        memberDTO.setName(" 클라리넷");
        memberDTO.setAddress("양산시");
        memberDTO.setPassword("1234");

        // dto -> entity, 암호화 적용
        return Member.createMember(memberDTO, passwordEncoder);
    }


    public Order createOrder(){
        Order order = new Order();
        for(int i=0; i<3; i++){
            Item item = this.createItem();
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);

            order.getOrderItems().add(orderItem); //주문서 정보(주문서,주문상품,수량,금액)
        }
        Member member = new Member();
        member = this.createMember();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);




        return order;
    }


    @Test
    @DisplayName("고아객체 제거 테스트 ")
    public void orphanRemoveTest(){
        Order order =this.createOrder();
        order.getOrderItems().remove(0);
        em.flush();

        // 부모 엔티티와 연관관계가 끊어졌기 때문에 고아객체를 삭제하는 쿼리문을 실행
        // Cascade REMOVE옵션인 경우 :
        // 부모 엔티티가 삭제될때 연관된 자식 엔티티도 함께 삭제
        // order를 삭제하면 order에 매핑되어 잇는 orederItem이 함께 삭제


    }


    @Test
    @DisplayName("지연 로딩 테스트")
    public void LazyLoading(){
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);

        log.info("order class"+ orderItem.getOrder().getClass());


    }











}
/*

 고아 객체 제거
 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 고아객체 \
 부모 엔티티를 통해서 자식의 생명주기를 관리
 주의사항 : 고아객체제거 기능은 참조하는 곳이 하나일때만 사용
 주로 @OenToOen , @OenTomany 어노테이션에 적용



 */