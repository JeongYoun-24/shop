package com.springboot.pople.repository;

import com.springboot.pople.entity.MovieImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieImgRepository extends JpaRepository<MovieImg,Long> {

    List<MovieImg> findByMovie_MovieidOrderByIdAsc(Long movieId);


    MovieImg findByMovie_MovieidAndRepImgYn(Long movieId, String repImgYn);

//    List<MovieImg> findByMovie_MovieidAndRepImgYn(Long movieId, String repImgYn);



}
