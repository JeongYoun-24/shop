package com.springstudy.shop.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ItemDto {
    // 클라이언트로 부터 넘어온 데이터 서버에서 데이터 검증

    private Long id;
    @NotBlank(message = "상품명은 필수 입력값입니다.")
    private String itemNm;
    @NotBlank(message = "상품명은 필수 입력값입니다.")
    private int price;

    private String itemDetail;
    private String itemSellCd;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

}
