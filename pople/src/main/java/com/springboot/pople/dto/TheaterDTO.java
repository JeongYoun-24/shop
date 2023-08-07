package com.springboot.pople.dto;


import com.springboot.pople.entity.Theater;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TheaterDTO {


    private Long id ;
    private String theaterName;
    private Long cinemaid; // 영화관 번호

    private static ModelMapper modelMapper = new ModelMapper();
    public static TheaterDTO of(Theater theater){
        // entity -> dto
        return modelMapper.map(theater, TheaterDTO.class);
    }

}
