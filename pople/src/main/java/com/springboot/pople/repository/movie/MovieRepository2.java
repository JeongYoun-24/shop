package com.springboot.pople.repository.movie;

import com.springboot.pople.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MovieRepository2 extends JpaRepository<Movie,Long> , QuerydslPredicateExecutor<Movie> {
}
