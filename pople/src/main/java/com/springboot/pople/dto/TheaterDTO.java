package com.springboot.pople.dto;

import com.springboot.pople.entity.Cinema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TheaterDTO {


    private Long id ;
    private Long cinemaid; // 영화관 번호



}
