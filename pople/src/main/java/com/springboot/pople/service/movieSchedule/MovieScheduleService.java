package com.springboot.pople.service.movieSchedule;

import com.springboot.pople.dto.MovieDTO;
import com.springboot.pople.dto.SeatDTO;
import com.springboot.pople.dto.TheaterDTO;
import com.springboot.pople.dto.movieschedule.MovieScheduleDTO;
import com.springboot.pople.dto.movieschedule.MovieScheduleFormDTO;
import com.springboot.pople.dto.theater.TheaterFormDTO;
import com.springboot.pople.entity.*;
import com.springboot.pople.repository.MovieScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class MovieScheduleService {

    private final MovieScheduleRepository movieScheduleRepository;
    private final ModelMapper modelMapper;
    public Long register(MovieScheduleDTO movieScheduleDTO){ // 등록
        log.info(movieScheduleDTO);
        Cinema cinema = Cinema.builder().cinemaid(movieScheduleDTO.getCinemaid()).build();
        Movie movie = Movie.builder().movieid(movieScheduleDTO.getMovieid()).build();
        Theater theater = Theater.builder().id(movieScheduleDTO.getTheaterid()).build();

        MovieSchedule schedule = MovieSchedule.builder()
                .cinema(cinema)
                .movie(movie)
                .theater(theater)
                .dayTime(movieScheduleDTO.getDayTime())
                .startTime(movieScheduleDTO.getStartTime())
                .endTime(movieScheduleDTO.getEndTime())
                .scheduleDate(LocalDateTime.now())
                .build();


//        MovieSchedule schedule = modelMapper.map(movieScheduleDTO, MovieSchedule.class);


        log.info("엔티티값 "+schedule);
        Long scheduleid = movieScheduleRepository.save(schedule).getId();


        return scheduleid;
    }

    public MovieScheduleDTO readOne (Long id){ // 조회
        Optional<MovieSchedule> result = movieScheduleRepository.findById(id);
        MovieSchedule schedule = result.orElseThrow();

        MovieScheduleDTO scheduleDTO = modelMapper.map(schedule, MovieScheduleDTO.class);

        return scheduleDTO;
    }
    public void modify(MovieScheduleDTO MovieScheduleDTO) { // 수정
        Optional<MovieSchedule> result = movieScheduleRepository.findById(MovieScheduleDTO.getId());
        MovieSchedule schedule = result.orElseThrow();

        schedule.update(MovieScheduleDTO.getStartTime(), MovieScheduleDTO.getEndTime());
        movieScheduleRepository.save(schedule);
    }

    public void remove(Long id) {
        movieScheduleRepository.deleteById(id);
    } // 삭제

    public List<MovieScheduleDTO> AllList() {
        List<MovieSchedule> cinemaList = movieScheduleRepository.findAll();
        log.info(cinemaList);
        MovieDTO cinemaDTO = modelMapper.map(cinemaList,MovieDTO.class);
        log.info(cinemaDTO);
        List<MovieScheduleDTO> cinemaDTOList = new ArrayList<>();
        log.info(cinemaDTOList);

        for(MovieSchedule seSchedule: cinemaList){
            MovieScheduleDTO movieImgDTO = MovieScheduleDTO.of(seSchedule);// entity->dto 메서드호출
            cinemaDTOList.add(movieImgDTO);

        }


        return cinemaDTOList;
    }

    public List<MovieScheduleDTO> MovieList(Long movieid) {
        log.info(movieid);
        List<MovieSchedule> seatList = movieScheduleRepository.findByMovie_Movieid(movieid);
        log.info("fsdfsdfsdfsdfsdfsd12155"+seatList);

        List<MovieScheduleDTO> seatFormDTOList = new ArrayList<>();
        for(MovieSchedule seat : seatList){
            MovieScheduleDTO seatListDTO = MovieScheduleDTO.of(seat);// entity->dto 메서드호출
            seatFormDTOList.add(seatListDTO);
        }
       // List<SeatDTO> theaterDTOList2 = new ArrayList<>();
//        for(SeatFormDTO formDTO : seatFormDTOList){
//            SeatDTO theaterListDTO2 = SeatDTO.builder()
//                    .seatNo(formDTO.getSeatNo())
//                    .seatLineNo(formDTO.getSeatLineNo())
//                    .seatGroup(formDTO.getSeatGroup())
//                    .build();// entity->dto 메서드호출
//            theaterDTOList2.add(theaterListDTO2);
//        }

        /*
        List<MovieScheduleFormDTO> theaterDTOList = new ArrayList<>();
        for(MovieSchedule theater : seatList){
            MovieScheduleFormDTO theaterListDTO = MovieScheduleFormDTO.of(theater);// entity->dto 메서드호출
            theaterDTOList.add(theaterListDTO);
        }
        List<MovieScheduleDTO> theaterDTOList2 = new ArrayList<>();
        for(MovieScheduleFormDTO formDTO : theaterDTOList){
            MovieScheduleDTO theaterListDTO2 = MovieScheduleDTO.builder()
                    .dayTime(formDTO.getDayTime())
                    .endTime(formDTO.getEndTime())
                    .startTime(formDTO.getStartTime())
                    .build();// entity->dto 메서드호출
            theaterDTOList2.add(theaterListDTO2);
        }
*/

        return seatFormDTOList;
    }



}
