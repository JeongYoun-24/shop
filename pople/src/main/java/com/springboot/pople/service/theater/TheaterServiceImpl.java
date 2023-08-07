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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class TheaterServiceImpl  implements  TheaterService{

    private final TheaterRepository theaterRepository;
    private final CinemaRepository cinemaRepository;

    private final ModelMapper modelMapper;

    @Override
    public Long register(TheaterDTO theaterDTO){

    //  dto -> entity
        Cinema cinema = Cinema.builder().cinemaid(theaterDTO.getCinemaid()).build();
        Theater theater = Theater.builder()
                .cinema(cinema)
                .build();

        log.info(theater);
        Long theaterid = theaterRepository.save(theater).getId();
        log.info(theaterid);

        return theaterid;
    }

    @Override
    public TheaterDTO readOne(Long id) {
        Optional<Theater> movie=  theaterRepository.findById(id);
        log.info("비상 값이 안나온다 "+movie);
        TheaterDTO theaterDTO = modelMapper.map(movie, TheaterDTO.class);
        log.info("비상 값이 안나온다 "+theaterDTO);
        return theaterDTO;
    }

    @Override
    public List<TheaterDTO> theaterList(Long cinemaid) {
        log.info(cinemaid);
        List<Theater> theaterList = theaterRepository.findByCinema_Cinemaid(cinemaid);
        log.info("fsdfsdfsdfsdfsdfsd12155"+theaterList);

        List<TheaterFormDTO> theaterDTOList = new ArrayList<>();
        for(Theater theater : theaterList){
            TheaterFormDTO theaterListDTO = TheaterFormDTO.of(theater);// entity->dto 메서드호출
            theaterDTOList.add(theaterListDTO);
        }
        List<TheaterDTO> theaterDTOList2 = new ArrayList<>();
        for(TheaterFormDTO formDTO : theaterDTOList){
            TheaterDTO theaterListDTO2 = TheaterDTO.builder()
                    .id(formDTO.getId())
                    .theaterName(formDTO.getTheaterName())
                    .build();// entity->dto 메서드호출
            theaterDTOList2.add(theaterListDTO2);
        }

        return theaterDTOList2;
    }


    @Override
    public List<TheaterDTO> AllList() {
        List<Theater> theaterList = theaterRepository.findAll();

//        log.info(theaterList);
//        TheaterDTO theaterDTO = modelMapper.map(theaterList,TheaterDTO.class);
//
        List<TheaterDTO> theaterDTOList = new ArrayList<>();

//        Cinema cinema = Cinema.builder().cinemaid(theaterDTO.getCinemaid()).build();
//        log.info(cinema);

        for(Theater theater : theaterList){
            log.info("111111"+theater.getCinema());
            TheaterDTO theaterListDTO = TheaterDTO.of(theater);// entity->dto 메서드호출
            log.info("값좀 나와라 "+theaterListDTO);
            theaterDTOList.add(theaterListDTO);

        }
        log.info(theaterDTOList);

        return theaterDTOList;
    }


}
