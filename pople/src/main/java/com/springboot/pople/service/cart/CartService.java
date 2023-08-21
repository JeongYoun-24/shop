package com.springboot.pople.service.cart;

import com.springboot.pople.dto.OrderDTO2;
import com.springboot.pople.dto.OrderHistDTO2;
import com.springboot.pople.dto.OrderItemDTO;
import com.springboot.pople.dto.item.CartDetailDTO;
import com.springboot.pople.dto.item.CartItemDTO;
import com.springboot.pople.dto.item.ItemDTO;
import com.springboot.pople.dto.item.ItemFormDTO;
import com.springboot.pople.entity.*;
import com.springboot.pople.repository.UsersRepository;
import com.springboot.pople.repository.cart.CartItemRepository;
import com.springboot.pople.repository.cart.CartRepository;
import com.springboot.pople.repository.item.ItemImgRepository;
import com.springboot.pople.repository.item.ItemRepository;
import com.springboot.pople.service.order.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class CartService {

    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final UsersRepository usersRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderService orderService;

    // 상품 장바구니에 담기
    public Long addCart(CartItemDTO cartItemDTO, String name){
        // 상품 엔티티 조회
        Item item = itemRepository.findById(cartItemDTO.getItemId()).orElseThrow(EntityNotFoundException::new);
            log.info("fdsfdsfs"+name);
        // 현재 로그인한 회원 정보
        Users users = usersRepository.findByName(name);

        log.info("테스스ㄹㅇㄴㄹㅇㄴㄹㄴㄹㅇ"+users);
        
        // 현재 로그인한 회원의 장바구니 엔티티 조회
        Cart cart = cartRepository.findByUsers_Userid(users.getUserid());
        log.info("ssss"+cart);
        // 현재 로그인한 회원이 상품을 처음으로 장바구니에 담을경우  => 회원장바구니 생성
        if(cart == null){
            cart = Cart.createCart(users); // 장바구니 생성
            cartRepository.save(cart);
        }
        // 현재 상품이 장바구니에 있는 조회
        CartItem saveCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(),item.getId());

        log.info("ssss"+saveCartItem);
        if(saveCartItem != null){
            // 장바구니에 이미 있던 상품일 경우 기존 수량에 현재 장바구니에 담을 수량 만큼을 더해 준다
            saveCartItem.addCount(cartItemDTO.getCount());
            return saveCartItem.getId();
        }else {
            // 장바구니 엔티티 , 장바구니에 담을 수량을 이용하여 cartItem엔티티 생성
            CartItem cartItem = CartItem.createCartItem(cart,item,cartItemDTO.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }

    }

    // 현재 로그인한 회원의 정보를 이용하여 장바구니에 들어 있는 상품을 조회하는 로직
    @Transactional(readOnly = true)
    public List<CartDetailDTO> getCartList(String name){
        List<CartDetailDTO> cartDetailDTOList = new ArrayList<>();

        // 현재 로그인한 회원의 장바구니 엔티티 조회
        Users users = usersRepository.findByName(name);
        Cart cart = cartRepository.findByUsers_Userid(users.getUserid());

        // 장바구니에 상품을 한 번도 안 담았을 경우 장바구니 엔티티가 빈리스트로 반환
        if (cart == null){
            return cartDetailDTOList;
        }

        // 장바구니에 담겨있는 상품정보 조회
        cartDetailDTOList = cartItemRepository.findCartDetailDTOList(cart.getId());

        return cartDetailDTOList;
    }

    // 장바구니 수정 권한을 체크
    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String name){
        // 현재 로그인 회원 조회
        Users curUsers = usersRepository.findByName(name);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        // 장바구니 상품을 저장한 회원 조회
        Users saveUsers = cartItem.getCart().getUsers();

        // 로그인 회원과 장바구니 회원이 다를 경우 false반환
        if (!StringUtils.equals(curUsers.getUserid(), saveUsers.getUserid())){
            return false;
        }
        return true;

    }
    // 장바구니 상품 수량을 업데이트하는 메서드
    public void updateCartItemCount(Long cartItemId, int count){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItem.updateCount(count);
    }


    // 장바구니에서 상품 삭제(상품번호를 파라미터로 받아서 삭제하는)메서드
   /* public void deleteCart(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItemRepository.delete(cartItem);
    }
    public Long orderCart(List<?> cartItemList,String name){
        List<OrderDTO2> orderDTO2List =new ArrayList<>();

        for(CartOrderDTO cartOrderDTO :cartItemList ){
            CartItem cartItem = cartItemRepository.findById(cartOrderDTO.getCartItemId)
                    .orElseThrow(EntityNotFoundException::new);

            OrderDTO2 orderDTO2 = new OrderDTO2();
            orderDTO2.setItemId(cartItem.getItem().getId());
            orderDTO2.setCount(cartItem.getCount());
            orderDTO2List.add(orderDTO2);
        }

        Long orderId = orderService.orders(orderDTO2List,name);
        for(CartOrderDTO cartOrderDTO : orderDTO2List){
            CartItem cartItem = cartItemRepository.findById(cartOrderDTO.getCartItemId)
                    .orElseThrow(EntityNotFoundException::new);

            cartItemRepository.delete(cartItem);

        }


        return orderId;
    }*/








//    // 현재 로그인한 회원의 정보를 이용하여 장바구니에 들어있는 삼품을 조회 하는 로직
//    @Transactional(readOnly = true)
//    public List<CartDetailDTO> cartList(String name,Pageable pageable){
//       List<CartDetailDTO> detailDTOList = new ArrayList<>();
//
//
//        // 현재 로그인한 회원의 장바구니 엔티티조회
//        Users users = usersRepository.findByName(name);
//        log.info("1234"+users);
//        Cart cart = cartRepository.findByUsers_Userid(users.getUserid());
//        log.info("1234"+cart);
//        // 장바구니에 상품을 하번도 안담았을 경우 장바구니 엔티티가 빈리스트로 반환
//        if(cart == null){
//            return detailDTOList;
//        }
//        log.info("1234"+cart.getId());
//        detailDTOList = cartItemRepository.findCartDetailDTOList(cart.getId());
//        log.info("1234"+detailDTOList);
//        return  detailDTOList;
//    }



    // 장바구니 상품 수량을 업데이트하는 메서드

    // 장바구니에서 상품 삭제 (삭제 )메서드


    // 장바구니에 담아 놓은 상품을 주문처리하는 메서드





    // 장바구니 조회
    /*@Transactional(readOnly = true)
    public Page<CartDetailDTO> cartList(String name, Pageable pageable){

        Users users = usersRepository.findByName(name);

        // 1. 아이디 장바구니 조회
        Cart carts = cartRepository.findByUsers_Userid(users.getUserid());
        log.info("11111"+carts);

        //2. 회원 장바구니에 상품 조회
        List<CartItem> cartItemList = cartItemRepository.findByCartId(carts.getId(),pageable);
        log.info("222222"+cartItemList);
        // 2. 사용자의 총 주문 개수
        Long totalCount = cartItemRepository.countCartItem(carts.getId());

        log.info("222222"+totalCount);
        // 3. 검색하여 가져온 주문 목록을 순회하여 구매이력 페이지에 전달할 DTO생성
        List<CartDetailDTO> orderHistDTOS = new ArrayList<>();

        for(CartItem cartItem : cartItemList){


                // 주문 상품 대표 이미지 조회: 주문서에 있는 상품entity로 통행 상품id추출하여 상품대표상품이미지 조회
                ItemImg itemImg = itemImgRepository
                        .findByItemIdAndRepImgYn((cartItem.getItem().getId()), "Y" );
            log.info("갑나오라ㅏ"+itemImg);

                Item item = itemRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
            log.info("갑나오라ㅏ"+item);

//            Long cartItemId,String itemName,int price , int count,String imgUrl
                // 주문 상품 정보(수량, 개수...), 상품 이미지
            CartDetailDTO orderItemDTO = new CartDetailDTO(cartItem.getId(),item.getItemName(),item.getPrice(),cartItem.getCount(),itemImg.getImgUrl());

                // 주문이력 리스트에 주문상품 추가
//                orderHistDTO.addOrderitemDTO2(orderItemDTO);



            orderHistDTOS.add(orderItemDTO); // 주문이력 List객체에 주문이력 상품 추가

            log.info("==> 고객이 주문한 주문서목록 (1:N)");
            log.info(orderHistDTOS.toString());
            log.info("===");

        } // end for (orders: 주문목록)




        return new PageImpl<>(orderHistDTOS, pageable, totalCount);
    }*/








}
