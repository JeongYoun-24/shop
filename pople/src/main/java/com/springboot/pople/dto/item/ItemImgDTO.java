package com.springboot.pople.dto.item;

import com.springboot.pople.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter @Setter@ToString
public class ItemImgDTO {

    private Long id;

    private String imgName;// 이미지 파일명
    private String oriImgName;// 원본 이미지 파일명
    private String imgUrl;// 이미지 조회 경로
    private String repImgYn; // 대표 이미지 여부

    private static ModelMapper modelMapper = new ModelMapper();
    public static ItemImgDTO of(ItemImg itemImg){
        // entity -> dto
        return modelMapper.map(itemImg, ItemImgDTO.class);
    }
}
