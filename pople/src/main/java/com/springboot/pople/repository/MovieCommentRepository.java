package com.springboot.pople.repository;

import com.springboot.pople.entity.Comment;
import com.springboot.pople.entity.Tickting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieCommentRepository  extends JpaRepository<Comment,Long> {


    @Query("select o from Comment o where o.movie.movieid = :movieid order by o.id desc")
    List<Comment> findComment(@Param("movieid") Long movieid, Pageable pageable);

    @Query("select count(o) from Comment o where o.movie.movieid = :movieid")
    Long countComment(@Param("movieid") Long movieid);


}
