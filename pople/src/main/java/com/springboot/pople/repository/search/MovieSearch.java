package com.springboot.pople.repository.search;

import com.springboot.pople.dto.MovieListCountDTO;
import com.springboot.pople.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieSearch {

    Page<Movie> search1(Pageable pageable);
    Page<Movie> searchAll(String[] types, String keyword, Pageable pageable);
    Page<MovieListCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable );


}
