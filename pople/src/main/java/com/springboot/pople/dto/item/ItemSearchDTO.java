package com.springboot.pople.dto.item;

import com.springboot.pople.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ItemSearchDTO {
    private String searchDateType;
    private ItemSellStatus searchSellStatus;
    private String searchBy;
    private String searchQuery = "";
}


/*
1. 현재 시간과 상품 등옥일을 비교해서 상품데이터 조회
 all: 상품 등록일 전체
 1d: 최근 하루 동안 등록된 상품
 1w: 최근 일주일 동안 등록된 상품
 1m: 최근 한달 동안 등록된 상품
 6m: 최근 6개월 동안 등록된 상품
2. 상품의 판매상태를 기준으로 상품데이터 조회
3. 어떤 유형별로 조회할지 선택
 itemNm: 상푸명, createdBy: 상품 등록자 아이디
4. 조회할 검색어를 기준으로 상품명 검색, createdBy 검색


 */