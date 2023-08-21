package com.springboot.pople.controller;

import com.springboot.pople.dto.item.CartDetailDTO;
import com.springboot.pople.dto.item.CartItemDTO;
import com.springboot.pople.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Log4j2
public class CartController {
    private final CartService cartService;

    // 1. 상세페이지에서 장바구니 담기 요청시 처리
    @PostMapping(value="/cart")
    public @ResponseBody ResponseEntity order(
            @RequestBody @Valid CartItemDTO cartItemDTO,
            BindingResult bindingResult, Principal principal
    ){

        // 장바구니에 담을 상품정보를 받는 cartItemDto객체에 데이터 검증하여 에러가 유무 체크
        if (bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            // 필드항목 순회하여 에러가 있을 경우 메시지 작성하여 응답
            for (FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        // 현재 로그인한 회원의 이메일(아이디역할) 정보를 변수에 저장
        String email = principal.getName();
        Long cartItemId;

        try {
            //  장바구니에 담을 상품정보와 현재 로그인한 회원의  이메일정보를 이용하여 장바구니에 상품을 담는 로직처리하는 서비스
            cartItemId = cartService.addCart(cartItemDTO, email);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        // 결과 값으로 생성된 장바구니 상품아이디와 요청이 성공하였으면 HTTP응답 상태코드 반환
        return new ResponseEntity(cartItemId, HttpStatus.OK);

    }

    // 장바구니 페이지 이동
    @GetMapping(value = {"/cart","cart/{page}"})
    public String orderHist(Principal principal, Model model,@PathVariable("page") Optional<Integer> page){
        log.info("==> /cart 요청");

        if(principal == null){
            model.addAttribute("msg","로그인후 이용가능합니다.");
            return "users/login";
        }
        Pageable pageable2 = PageRequest.of(page.isPresent()?page.get():0, 5);

        // 현재 로그인한 사용자의 이메일(아이디) 정보를 이용하여 장바구니에 담겨있는 상품정보조회 서비스요청
        List<CartDetailDTO> cartDetailDTOList = cartService.getCartList(principal.getName());

        // 조회한 장바구니 상품정보를 model에 담아 뷰로 전달
        model.addAttribute("cartItems", cartDetailDTOList);

        return "cart/cartList";
    }



    // REST API에서 요청 자원의 일부만 처리시 PATCH를 사용
    // 장바구니 상품의 수량만 처리 (데이터만 처리) : @PathMaapping적용
    @PatchMapping(value ="/cartItem/{id}")
    public @ResponseBody ResponseEntity updateCartItem(@PathVariable("id") Long cartItemId, int count, Principal principal
    ){
        log.info(cartItemId);
        log.info("전달 받나요??");
        if (count <=0 ){
            // 장바구니에 담겨있는 상품의 개수가 0이하이면 업데이트 요청을 할 때 에러메시지를 담아 반환
            return new ResponseEntity<String>("최소 1개이상 담아 주세요/",HttpStatus.BAD_REQUEST);
        }else if (!cartService.validateCartItem(cartItemId, principal.getName())){
            // 수정 권한을 체크(로그인 한 회원과 장바구니 회원일 일치한 경우)
            return new ResponseEntity<String>("수정 권한이 없습니다.",HttpStatus.FORBIDDEN);
        }

        // 장바구니 상품 수량 업데이트
        cartService.updateCartItemCount(cartItemId, count);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }




    @PostMapping(value = "/Delete/{id}")
    public @ResponseBody ResponseEntity deleteCart(@PathVariable("id")Long cartItemId,Principal principal){
        log.info(cartItemId);
        log.info("전달 받나요??");

//        if (!cartService.validateCartItem(id, principal.getName())){
//            // 수정 권한을 체크(로그인 한 회원과 장바구니 회원일 일치한 경우)
//            return new ResponseEntity<String>("삭제 권한이 없습니다.",HttpStatus.FORBIDDEN);
//        }

        // 장바구니 상품 삭제
        cartService.deleteCart(cartItemId);
        return new ResponseEntity<Long>(cartItemId,HttpStatus.OK);
    }



}
