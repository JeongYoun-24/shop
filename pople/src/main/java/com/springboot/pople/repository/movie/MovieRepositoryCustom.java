package com.springboot.pople.repository.movie;

import com.springboot.pople.dto.movie.MainMovieDTO;
import com.springboot.pople.dto.movie.MovieSearchDTO;
import com.springboot.pople.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieRepositoryCustom {
    Page<Movie> getAdminMoviePage(MovieSearchDTO movieSearchDTO, Pageable pageable);
    Page<MainMovieDTO> getMainItemPage(MovieSearchDTO movieSearchDTO, Pageable pageable);

}
