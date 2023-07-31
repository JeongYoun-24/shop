package com.springboot.pople.repository;

import com.springboot.pople.entity.MovieImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieImgRepository extends JpaRepository<MovieImg,Long> {

    List<MovieImg> findByIdOrderByIdAsc(Long Id);


}
