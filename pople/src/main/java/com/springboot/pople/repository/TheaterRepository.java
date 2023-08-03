package com.springboot.pople.repository;

import com.springboot.pople.entity.MovieImg;
import com.springboot.pople.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater,Long> {

//    List<Theater>findById(Long cinemaId);

//    List<MovieImg> findByMovie_MovieidOrderByIdAsc(Long movieId);
}
