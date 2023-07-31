package com.springboot.pople.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CINEMA")
public class Cinema {

    @Id
    @Column(nullable = false,name = "cinema_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cinemaid;
    @Column(nullable = false,length = 50)
    private String cinemaName;
    @Column(nullable = false,length = 500)
    private String cinemaAddrss;
    @Column(nullable = false)
    private long cinemaSeatCount;

    @Column(name = "x_axis")
    private String xaxis;  // x좌표
    @Column(name = "y_axis")
    private String yaxis; // y좌표

    public void change(String cinemaName,String cinemaAddrss,long cinemaSeatCount){
        this.cinemaName = cinemaName;
        this.cinemaAddrss = cinemaAddrss;
        this.cinemaSeatCount = cinemaSeatCount;

    }



}




