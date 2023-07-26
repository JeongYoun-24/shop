package com.springstudy.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "item_img")
@Getter
@Setter
public class ItemImg extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_img_id")
    private Long id;

    private String imgName ; // 이미지 파일명
    private String oriImgName; // 원본 이미지 파일명
    private String imgUrl; // 이미지 조회 경로
    private String repImgYn; // 대표이미지 여부
    // 1개의 상품에 여러개의 이미지  연관관계 1(Item): N(Item_Img)
    // 상품 엔티티 정보가 필요한 경우 데이터를 조회
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // 원본 이미지 이름 , 이미지 이름 , 이미지 경로
    public void updateItemImg(String oriImgName,String imgName,String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;

    }


}

/*

item        1:N(이미지)      item_img
===================================
item_id(pk)                 item-img_id(pk)
item_nm                     item-id(fk)
price


*/