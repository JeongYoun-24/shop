package com.springboot.pople.repository;

import com.springboot.pople.entity.MovieSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieScheduleRepository extends JpaRepository<MovieSchedule,Long> {

     List<MovieSchedule>findByMovie_Movieid(Long moiveid);

}
