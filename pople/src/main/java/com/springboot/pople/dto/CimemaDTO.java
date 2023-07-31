package com.springboot.pople.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CimemaDTO {


    private long cinemaid;
 
    private String cinemaName;

    private String cinemaAddrss;

    private long cinemaSeatCount;

    @Column(name = "x_axis")
    private String xaxis;  // x좌표
    @Column(name = "y_axis")
    private String yaxis; // y좌표

}
