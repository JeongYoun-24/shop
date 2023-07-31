package com.springboot.pople.repository;

import com.springboot.pople.entity.Movie;
import com.springboot.pople.repository.search.MovieSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> , MovieSearch{



}
