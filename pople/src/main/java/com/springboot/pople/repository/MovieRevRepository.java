package com.springboot.pople.repository;

import com.springboot.pople.entity.Comment;
import com.springboot.pople.entity.MovieRev;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRevRepository extends JpaRepository<MovieRev,Long> {

    @Query("select o from MovieRev o where o.movie.movieid = :movieid order by o.revid desc")
    List<MovieRev> findMovieRev(@Param("movieid") Long movieid, Pageable pageable);

    @Query("select count(o) from MovieRev o where o.movie.movieid = :movieid")
    Long countMovieRev(@Param("movieid") Long movieid);



}
