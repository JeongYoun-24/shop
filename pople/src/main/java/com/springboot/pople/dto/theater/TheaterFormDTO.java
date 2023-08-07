package com.springboot.pople.dto.theater;

import com.springboot.pople.dto.TheaterDTO;
import com.springboot.pople.entity.Cinema;
import com.springboot.pople.entity.Theater;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TheaterFormDTO {

    private Long id ;
    private String theaterName;

    private Cinema cinema;

    private static ModelMapper modelMapper = new ModelMapper();
    public static TheaterFormDTO of(Theater theater){
        // entity -> dto
        return modelMapper.map(theater, TheaterFormDTO.class);
    }

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "cinema_id")
//    private Cinema cinema;


}
