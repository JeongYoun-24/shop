package com.springboot.pople.service.theater;

import com.springboot.pople.dto.CinemaDTO;
import com.springboot.pople.dto.MovieDTO;
import com.springboot.pople.dto.TheaterDTO;
import com.springboot.pople.dto.theater.TheaterFormDTO;
import com.springboot.pople.entity.Cinema;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.entity.Theater;
import com.springboot.pople.repository.CinemaRepository;
import com.springboot.pople.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface TheaterService {

    public Long register(TheaterDTO theaterDTO);

    public TheaterDTO readOne(Long cinemaid);


    public List<TheaterDTO> theaterList(Long cinemaid);



    public List<TheaterDTO> AllList();

}
