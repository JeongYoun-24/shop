package com.springboot.pople.dto;

import com.springboot.pople.entity.BoardImg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@ToString
public class BoardImgDTO {


    private Long id;

    private String imgName;// 이미지 파일명
    private String oriImgName;// 원본 이미지 파일명
    private String imgUrl;// 이미지 조회 경로
    private String repImgYn; // 대표 이미지 여부

    private static ModelMapper modelMapper = new ModelMapper();
    public static BoardImgDTO of(BoardImg boardImg){
        // entity -> dto
        return modelMapper.map(boardImg, BoardImgDTO.class);
    }

}
