package com.springboot.pople.dto;

import com.springboot.pople.entity.Cinema;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.entity.Theater;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieScheduleDTO {


    private Long id;

    private Long theaterid;

    private Long cinemaid;

    private Long movieid;
    private String dayTime; // 상영 시간  (아침 점심 저녁 새벽 )


    private LocalDateTime scheduleDate; // 영화 날짜
    private Time startTime;  // 영화 시작
    private Time endTime;    // 영화 종료


}
