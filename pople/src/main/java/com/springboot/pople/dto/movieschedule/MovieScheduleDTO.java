package com.springboot.pople.dto.movieschedule;

import com.springboot.pople.dto.SeatFormDTO;
import com.springboot.pople.entity.*;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
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
    private String startTime;  // 영화 시작
    private String endTime;    // 영화 종료



    private static ModelMapper modelMapper = new ModelMapper();
    public static MovieScheduleDTO of(MovieSchedule schedule){
        // entity -> dto
        return modelMapper.map(schedule, MovieScheduleDTO.class);
    }
}
