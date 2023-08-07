package com.springboot.pople.dto;

import com.springboot.pople.dto.movie.MovieImgDTO;
import com.springboot.pople.entity.Cinema;
import com.springboot.pople.entity.MovieImg;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CinemaDTO {

    private static ModelMapper modelMapper = new ModelMapper();

    private Long cinemaid;
    @NotEmpty(message = "영화관이름은 필수 입력 값입니다.")
    private String cinemaName;
    @NotEmpty(message = "영화관주소는 필수 입력 값입니다.")
    private String cinemaAddrss;
    @NotEmpty(message = "영화관좌석은 필수 입력 값입니다.")
    private int cinemaSeatCount;

    private String xaxis;  // x좌표

    private String yaxis; // y좌표


    public static CinemaDTO of(Cinema cinema){
        // entity -> dto
        return modelMapper.map(cinema, CinemaDTO.class);
    }


}
