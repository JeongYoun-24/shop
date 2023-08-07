package com.springboot.pople.service.seat;

import com.springboot.pople.dto.CinemaDTO;
import com.springboot.pople.dto.SeatDTO;
import com.springboot.pople.dto.SeatFormDTO;
import com.springboot.pople.dto.TheaterDTO;
import com.springboot.pople.dto.theater.TheaterFormDTO;
import com.springboot.pople.entity.Cinema;
import com.springboot.pople.entity.Movie;
import com.springboot.pople.entity.Seat;
import com.springboot.pople.entity.Theater;
import com.springboot.pople.repository.SeatRepository;
import com.springboot.pople.repository.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final ModelMapper modelMapper;
    private final SeatRepository seatRepository;//dao
    @Override
    public Long register(SeatDTO seatDTO) {
//        Seat seat = modelMapper.map(seatDTO, Seat.class);
//        Long seatid = seatRepository.save(seat).getSeatid();
        Cinema cinema = modelMapper.map(seatDTO,Cinema.class);
        Theater theater = modelMapper.map(seatDTO,Theater.class);
        cinema.setCinemaid(seatDTO.getCinemaid());
        theater.setId(seatDTO.getTheaterid());
        log.info(theater.getId());
        log.info(cinema.getCinemaid());


//        Cinema cinema = Cinema.builder().cinemaid(seatDTO.getCinemaid()).build();
//        Theater theater = Theater.builder().id(seatDTO.getTheaterid()).build();
//
        Seat seat = Seat.builder()
                .seatNo(seatDTO.getSeatNo())
                .seatGroup(seatDTO.getSeatGroup())
                .seatLineNo(seatDTO.getSeatLineNo())
                .cinema(cinema)
                .theater(theater)
                .build();
        Seat seat1 = new Seat();



//
        log.info("1111"+seat);
        Long seatid = seatRepository.save(seat).getSeatid();
//        log.info(seatid);


        return seatid;
    }

    @Override
    public SeatDTO readOne(Long seatid) {
        return null;
    }

    @Override
    public void modify(SeatDTO seatDTO) {

    }

    @Override
    public void remove(Long seatid) {

    }

    @Override
    public List<SeatDTO> theaterList(Long theaterid) {
        log.info(theaterid);
        List<Seat> seatList = seatRepository.findByTheater_Id(theaterid);
        log.info("fsdfsdfsdfsdfsdfsd12155"+seatList);

        List<SeatDTO> seatFormDTOList = new ArrayList<>();
        for(Seat seat : seatList){
            SeatDTO seatListDTO = SeatDTO.of(seat);// entity->dto 메서드호출
            seatFormDTOList.add(seatListDTO);
        }
        List<SeatDTO> theaterDTOList2 = new ArrayList<>();
//        for(SeatFormDTO formDTO : seatFormDTOList){
//            SeatDTO theaterListDTO2 = SeatDTO.builder()
//                    .seatNo(formDTO.getSeatNo())
//                    .seatLineNo(formDTO.getSeatLineNo())
//                    .seatGroup(formDTO.getSeatGroup())
//                    .build();// entity->dto 메서드호출
//            theaterDTOList2.add(theaterListDTO2);
//        }
        return seatFormDTOList;
    }

    @Override
    public List<SeatDTO> AllList() {
        List<Seat> seatList = seatRepository.findAll();
        log.info(seatList);
        SeatDTO seatDTO = modelMapper.map(seatList,SeatDTO.class);
        log.info(seatDTO);
        List<SeatDTO> seatDTOList = new ArrayList<>();
        log.info(seatDTOList);

        for(Seat seat: seatList){
            SeatDTO seatListDTO = SeatDTO.of(seat);// entity->dto 메서드호출
            seatDTOList.add(seatListDTO);
        }

        return seatDTOList;
    }
}
