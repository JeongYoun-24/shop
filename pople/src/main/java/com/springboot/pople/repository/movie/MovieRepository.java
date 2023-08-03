package com.springboot.pople.repository.movie;

import com.springboot.pople.dto.movie.MainMovieDTO;
import com.springboot.pople.dto.movie.MovieSearchDTO;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.repository.MovieScheduleRepository;
import com.springboot.pople.repository.search.MovieSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MovieRepository extends JpaRepository<Movie,Long>, QuerydslPredicateExecutor<Movie>, MovieSearch,MovieRepositoryCustom {


}
