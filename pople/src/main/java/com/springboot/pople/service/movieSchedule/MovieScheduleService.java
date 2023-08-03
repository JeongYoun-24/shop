package com.springboot.pople.service.movieSchedule;

import com.springboot.pople.dto.MovieDTO;
import com.springboot.pople.dto.MovieScheduleDTO;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.entity.MovieSchedule;
import com.springboot.pople.entity.Theater;
import com.springboot.pople.repository.MovieScheduleRepository;
import com.springboot.pople.service.movie.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class MovieScheduleService {

    private final MovieScheduleRepository movieScheduleRepository;
    private final ModelMapper modelMapper;
    public Long register(MovieScheduleDTO movieScheduleDTO){ // 등록
        MovieSchedule schedule = modelMapper.map(movieScheduleDTO, MovieSchedule.class);
        Long scheduleid = movieScheduleRepository.save(schedule).getId();


        return scheduleid;
    }

    public MovieScheduleDTO readOne (Long id){ // 조회
        Optional<MovieSchedule> result = movieScheduleRepository.findById(id);
        MovieSchedule schedule = result.orElseThrow();

        MovieScheduleDTO scheduleDTO = modelMapper.map(schedule, MovieScheduleDTO.class);

        return scheduleDTO;
    }
    public void modify(MovieScheduleDTO MovieScheduleDTO) { // 수정
        Optional<MovieSchedule> result = movieScheduleRepository.findById(MovieScheduleDTO.getId());
        MovieSchedule schedule = result.orElseThrow();

        schedule.update(MovieScheduleDTO.getStartTime(), MovieScheduleDTO.getEndTime());
        movieScheduleRepository.save(schedule);
    }

    public void remove(Long id) {
        movieScheduleRepository.deleteById(id);
    } // 삭제



}
