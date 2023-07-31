package com.springboot.pople.service.movie;

import com.springboot.pople.dto.MovieDTO;
import com.springboot.pople.dto.MovieListCountDTO;
import com.springboot.pople.dto.PageRequestDTO;
import com.springboot.pople.dto.PageResponseDTO;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.repository.MovieRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MovieServiceImpl implements MovieService  {

    private final ModelMapper modelMapper;
    private final MovieRepository movieRepository;//dao




    @Override
    public Long register(MovieDTO movieDTO) {
        Movie movie = modelMapper.map(movieDTO, Movie.class);
        Long movieid = movieRepository.save(movie).getMovieid();


        return movieid;
    }

    @Override
    public MovieDTO readOne(Long movieid) {
        Optional<Movie> result = movieRepository.findById(movieid);
        Movie movie = result.orElseThrow();

        MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);

        return movieDTO;
    }

    @Override
    public void modify(MovieDTO movieDTO) {
        Optional<Movie> result = movieRepository.findById(movieDTO.getMovieid());
        Movie movie = result.orElseThrow();

        movie.change(movieDTO.getMovieName(), movieDTO.getMoviePoster(),movieDTO.getMovieSummary());
        movieRepository.save(movie);
    }

    @Override
    public void remove(Long movieid) {
        movieRepository.deleteById(movieid);
    }

//    @Override
//    public PageResponseDTO<MovieDTO> list(PageRequestDTO pageRequestDTO) {
//
//        String [] types = pageRequestDTO.getTypes();
//        String keyword = pageRequestDTO.getKeyword();
//        Pageable pageable = pageRequestDTO.getPageable("movie_code");
//
//        // 레포지토리에서 entity데이터 추출
//        Page<Movie> result = movieRepository.searchAll(types, keyword, pageable);
//        // entity -> dto
//        List<MovieDTO> dtoList = result.getContent().stream()
//                .map(movie -> modelMapper.map(movie, MovieDTO.class))
//                .collect(Collectors.toList());
//
//        return PageResponseDTO.<MovieDTO>widthAll()
//                .pageRequestDTO(pageRequestDTO)
//                .dtoList(dtoList)
//                .total((int)result.getTotalElements())
//                .build();
//    }
//
//    @Override
//    public PageResponseDTO<MovieListCountDTO> listReplyCount(PageRequestDTO pageRequestDTO) {
//        String[] types = pageRequestDTO.getTypes();
//        String keyword = pageRequestDTO.getKeyword();
//        Pageable pageable = pageRequestDTO.getPageable("movie_code");
//
//        Page<MovieListCountDTO> result = movieRepository.searchWithReplyCount(types, keyword, pageable);
//
//
//        return PageResponseDTO.<MovieListCountDTO>widthAll()
//                .pageRequestDTO(pageRequestDTO)
//                .dtoList(result.getContent())
//                .total((int)result.getTotalElements())
//                .build();
//    }


}
