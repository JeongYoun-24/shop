package com.springboot.pople.service.movierev;


import com.springboot.pople.dto.CommentFormDTO;
import com.springboot.pople.dto.MovieDTO;
import com.springboot.pople.dto.MovieRevDTO;
import com.springboot.pople.dto.MovieRevFormDTO;
import com.springboot.pople.dto.movie.MovieFormDTO;
import com.springboot.pople.entity.Comment;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.entity.MovieRev;
import com.springboot.pople.entity.Users;
import com.springboot.pople.repository.MovieRevRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MovieRevServiceImpl implements MovieRevService {

    private final MovieRevRepository movieRevRepository;
    @Override
    public Long register(MovieRevDTO movieRevDTO) {
        Movie movie = Movie.builder().movieid(movieRevDTO.getMovieId()).build();

        Users users =Users.builder().userid(movieRevDTO.getUserId()).build();


        MovieRev comment = MovieRev.builder()
                .movie(movie)
                .users(users)
                .revContent(movieRevDTO.getRevContent())
                .revTitle(movieRevDTO.getRevTitle())
                .star(movieRevDTO.getStar())
                .revDate(LocalDateTime.now())
                .build();


        Long id = movieRevRepository.save(comment).getRevid();

        return id;
    }

    @Override
    public MovieRevFormDTO readOne(Long revid) {
        Optional<MovieRev> result = movieRevRepository.findById(revid);
        MovieRev rev = result.orElseThrow();


//        CommentDTO boardDto = modelMapper.map(board, CommentDTO.class);

        MovieRevFormDTO movieRevFormDTO = MovieRevFormDTO.builder()
                .revid(rev.getRevid())
                .users(rev.getUsers())
                .movie(rev.getMovie())
                .revTitle(rev.getRevTitle())
                .revContent(rev.getRevContent())
                .star(rev.getStar())
                .revDate(rev.getRevDate())
                .build();

        return movieRevFormDTO;
    }

    @Override
    public void modify(MovieRevDTO movieRevDTO) {

    }

    @Override
    public void remove(Long revid) {
        movieRevRepository.deleteById(revid);

    }

    @Override
    public Page<MovieRevFormDTO> findList(Long movieid, Pageable pageable) {
        List<MovieRev> movieRev = movieRevRepository.findMovieRev(movieid,pageable);

        // 영화에 해당하는댓글 총 갯수
        Long totalCount = movieRevRepository.countMovieRev(movieid);
        log.info(""+movieRev);
        List<MovieRevFormDTO> revFormDTOList= new ArrayList<>();
        for(MovieRev rev : movieRev){
            MovieRevFormDTO revFormDTO = MovieRevFormDTO.builder()
                    .revid(rev.getRevid())
                    .users(rev.getUsers())
                    .movie(rev.getMovie())
                    .revTitle(rev.getRevTitle())
                    .revContent(rev.getRevContent())
                    .star(rev.getStar())
                    .revDate(rev.getRevDate())
                    .build();
            revFormDTOList.add(revFormDTO);

        }
        log.info("fsdfsdfsdfsd"+revFormDTOList);

        return new PageImpl<>(revFormDTOList, pageable, totalCount);
    }
}
