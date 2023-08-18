package com.springboot.pople.dto.item;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class MainItemDTO {

    private Long id;
    private String itemNm;
    private String itemDetail;
    private String imgUrl;
    private Integer price;

    // Entity->DTO변환과정 없이 DTO객체 값을 바로추출
    @QueryProjection
    public MainItemDTO(Long id, String itemNm, String itemDetail, String imgUrl, Integer price){
        this.id = id;
        this.itemNm = itemNm;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
    }

}
