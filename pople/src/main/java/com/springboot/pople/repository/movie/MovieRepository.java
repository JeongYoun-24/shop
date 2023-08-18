package com.springboot.pople.repository.movie;

import com.springboot.pople.dto.MovieDTO;
import com.springboot.pople.dto.movie.MainMovieDTO;
import com.springboot.pople.dto.movie.MovieSearchDTO;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.entity.Users;
import com.springboot.pople.repository.MovieScheduleRepository;
import com.springboot.pople.repository.search.MovieSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long>, QuerydslPredicateExecutor<Movie>, MovieSearch,MovieRepositoryCustom {


    Movie findByMovieName(String movieName);


    @Query("select i from Movie i where i.movieName like %:movieName% order by i.movieDate desc")
    List<Movie> findByMovieNameList(@Param("movieName") String movieName);


//    Users findByEmail(String email);
}
