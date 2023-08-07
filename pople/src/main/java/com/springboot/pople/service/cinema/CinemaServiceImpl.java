package com.springboot.pople.service.cinema;

import com.springboot.pople.dto.CinemaDTO;
import com.springboot.pople.dto.TheaterDTO;
import com.springboot.pople.dto.movie.MovieImgDTO;
import com.springboot.pople.entity.Cinema;
import com.springboot.pople.entity.MovieImg;
import com.springboot.pople.entity.Theater;
import com.springboot.pople.entity.Users;
import com.springboot.pople.repository.CinemaRepository;
import com.springboot.pople.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {

    private final ModelMapper modelMapper;

    private final CinemaRepository cinemaRepository;


    @Override
    public Long register(CinemaDTO cinemaDTO) {
        Cinema cinema = modelMapper.map(cinemaDTO,Cinema.class);
        Long cinemaid = cinemaRepository.save(cinema).getCinemaid();

        return cinemaid;
    }

    @Override
    public CinemaDTO readOne(Long cinemaid) {
        Optional<Cinema> movie=  cinemaRepository.findById(cinemaid);
        log.info("비상 값이 안나온다 "+movie);
        CinemaDTO theaterDTO = modelMapper.map(movie, CinemaDTO.class);
        log.info("비상 값이 안나온다 "+theaterDTO);
        return theaterDTO;

    }

    @Override
    public void modify(CinemaDTO cinemaDTO) {

    }

    @Override
    public void remove(Long cinemaid) {

    }

    @Override
    public List<CinemaDTO> AllList() {
        List<Cinema> cinemaList = cinemaRepository.findAll();
        log.info(cinemaList);
        CinemaDTO cinemaDTO = modelMapper.map(cinemaList,CinemaDTO.class);
        log.info(cinemaDTO);
        List<CinemaDTO> cinemaDTOList = new ArrayList<>();
        log.info(cinemaDTOList);

        for(Cinema cinema: cinemaList){
            CinemaDTO movieImgDTO = CinemaDTO.of(cinema);// entity->dto 메서드호출
            cinemaDTOList.add(movieImgDTO);

        }



        return cinemaDTOList;
    }


}
