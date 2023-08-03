package com.springboot.pople.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class MovieSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "theater_id") //상영관 FK
    private Theater theater;

    @ManyToOne
    @JoinColumn(name = "cinema_id")  // 영화관 FK
    private Cinema cinema;

    @ManyToOne
    @JoinColumn(name = "movie_id") // 영화 FK
    private Movie movie;
    private String dayTime;


    private LocalDateTime scheduleDate; // 영화 날짜
    private Time startTime;  // 영화 시작
    private Time endTime;    // 영화 종료


    public void update(Time startTime,Time endTime){
        this.startTime = startTime;
        this.endTime = endTime;


    }


}
