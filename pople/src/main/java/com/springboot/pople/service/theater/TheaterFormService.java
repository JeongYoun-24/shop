package com.springboot.pople.service.theater;

import com.springboot.pople.dto.TheaterDTO;
import com.springboot.pople.dto.theater.TheaterFormDTO;
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

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class TheaterFormService {
    private final TheaterRepository theaterRepository;
    private final CinemaRepository cinemaRepository;

    private final ModelMapper modelMapper;
    public List<TheaterFormDTO> AllList() {
        List<Theater> theaterList = theaterRepository.findAll();

        List<TheaterFormDTO> theaterDTOList = new ArrayList<>();

        for(Theater theater : theaterList){
            log.info("111111"+theater.getCinema());
            TheaterFormDTO theaterListDTO = TheaterFormDTO.of(theater);// entity->dto 메서드호출
            log.info("값좀 나와라 "+theaterListDTO);
            theaterDTOList.add(theaterListDTO);

        }
        log.info(theaterDTOList);

        return theaterDTOList;
    }

    public TheaterFormDTO theaterName(String theaterName) {
       Theater theater=  theaterRepository.findByTheaterName(theaterName);
        log.info("비상 값이 안나온다 "+theater);
        TheaterFormDTO theaterDTO = modelMapper.map(theater, TheaterFormDTO.class);
        log.info("비상 값이 안나온다 "+theaterDTO);

        return theaterDTO;
    }




}
