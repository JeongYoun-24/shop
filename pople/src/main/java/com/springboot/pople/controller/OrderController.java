package com.springboot.pople.controller;

import com.springboot.pople.dto.OrderDTO;
import com.springboot.pople.dto.OrderDTO2;
import com.springboot.pople.dto.OrderHistDTO2;
import com.springboot.pople.dto.item.CartDetailDTO;
import com.springboot.pople.dto.item.OrderDetailDTO;
import com.springboot.pople.entity.Item;
import com.springboot.pople.entity.OrderItem;
import com.springboot.pople.entity.Users;
import com.springboot.pople.repository.UsersRepository;
import com.springboot.pople.service.UsersService;
import com.springboot.pople.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Log4j2
public class OrderController {

    private final OrderService orderService;
    private final UsersService usersService;
    private final UsersRepository usersRepository;



    // 1. 상품 주문하기
    /* 스프링에서 비동기 처리 요청시 처리 : @RequestBody, @ResponseBody을 적용 */
    // @RequestBody: Http요청의 본문 body에 담긴 내용을 자바 객체로 전달
    // @ResponseBody: 자바 객체를 Http요청의 body로 전달
    @PostMapping(value="/order")
    public @ResponseBody ResponseEntity order(
            @RequestBody @Valid OrderDTO2 orderDTO,
            BindingResult bindingResult,
            Principal principal){

        // 주문 정보를 받은 orderDTO객체 데이터 바인딩시 에러가 있는 체크
        if (bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();

            // FieldError: 지정한 에러가 발생하면 입력값이 초기화
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError: fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }

            // 데이터 검증 결과 에러시 , 에러코드와 메시지 전송
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        log.info("==> orderDTO:"+orderDTO.toString());

        String email = principal.getName();// 로그인 아이디(이메일)
        Long orderId;
        try{
            orderId = orderService.order(orderDTO, email);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        // 주문이 정상적으로 처리되면 생성된 주문번호와 요청 성공했다는 http응답 상태코드를 반환
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    // 2. 회원(고객) 구매 이력 조회
    @GetMapping(value = {"/orders","/orders/{page}"} )
    public String orderHist(@PathVariable("page")Optional<Integer> page, Principal principal, Model model){

        if(principal == null){

            return "redirect:user/login";
        }


        // 페이지 번호가 매개변수에 없으면 0으로 설정, 페이지에 보여질 레코드 수)
        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0, 4);
        log.info("이거맞냐??"+principal.getName());

        Page<OrderDetailDTO> cartDetailDTOList = orderService.getCartList(principal.getName(),pageable);

        log.info("이거맞냐??"+cartDetailDTOList);

    // 현재 로그인한 회원의 이메일과 페이징 객체를 인자로 전달하여 구매이력을 조회하는 서비스 요청
//        Page<OrderHistDTO2> orderHistDTOList =
//                orderService.getOrderList(principal.getName(),pageable );
//
//
        model.addAttribute("orders", cartDetailDTOList);
        model.addAttribute("page", pageable.getPageNumber());// 총 페이지 수
        model.addAttribute("maxPage", 5);// 한줄에 보여질 페이지 번호 개수

//        log.info("==> 구매이력");
//        orderHistDTOList.getContent().forEach(x -> {
//            log.info(x);
//        });

        return "order/orderItemList";
    }

    // 3. 주문 취소 처리
    @PostMapping(value="/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancelOrder(
            @PathVariable("orderId") Long orderId, Principal principal){

        log.info("==> 주문취소 상품: "+orderId);
        // 주문 취소 권한 검사
        if (!orderService.validateOrder(orderId, principal.getName())){
            return new ResponseEntity("주문 취소 권한이 없습니다.",HttpStatus.FORBIDDEN);
        }
        log.info("==> 주문취소 요청: "+orderId);
        // 현재 로그인 사용자 상품 주문한 사용자가 동일하면 주문취소 서비스 처리
        orderService.cancelOrder(orderId);

        return new ResponseEntity(orderId, HttpStatus.OK);

    }






}
