package com.springboot.pople.dto.item;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartItemDTO { // 상품 상세 페이지 에서 장바구니에 담을 상품 아이디와 수량 전달 받을 DTO

    @NotNull(message = "상품 아이디는 필수 입력값 입니다.")
    private Long itemId;
    @Min(value = 1,message = "최소1개 이상 담아주세요")
    private int count;




}
