package com.springboot.pople.dto.movie;

import com.springboot.pople.entity.MovieImg;
import com.springboot.pople.entity.OrderMovie;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@ToString
public class MovieImgDTO {

    private Long id;

    private String imgName;// 이미지 파일명
    private String oriImgName;// 원본 이미지 파일명
    private String imgUrl;// 이미지 조회 경로
    private String repImgYn; // 대표 이미지 여부

    private static ModelMapper modelMapper = new ModelMapper();
    public static MovieImgDTO of(MovieImg movieImg){
        // entity -> dto
        return modelMapper.map(movieImg, MovieImgDTO.class);
    }


//    public MovieImgDTO(MovieImg ordermovie, String imgUrl){
//        this.imgName = ordermovie.getMovie().getMovieName();
//        this.imgUrl = imgUrl;
//    }


}
