package com.springboot.pople.dto.movieschedule;

import com.springboot.pople.entity.Cinema;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.entity.MovieSchedule;
import com.springboot.pople.entity.Theater;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class MovieScheduleFormDTO {


    private Long id;

    private Theater theater; // 상영관

    private Cinema cinema; // 영화관

    private Movie movie; // 영화
    private String dayTime; // 영화 시간 (오전 점심 저녁 오후 새벽)

    private LocalDateTime scheduleDate; // 영화 날짜
    private String startTime;  // 영화 시작
    private String endTime;    // 영화 종료


    private static ModelMapper modelMapper = new ModelMapper();
    public static MovieScheduleFormDTO of(MovieSchedule schedule){
        // entity -> dto
        return modelMapper.map(schedule, MovieScheduleFormDTO.class);
    }

}
