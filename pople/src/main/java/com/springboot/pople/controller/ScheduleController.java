package com.springboot.pople.controller;

import com.springboot.pople.dto.CinemaDTO;
import com.springboot.pople.dto.MovieDTO;
import com.springboot.pople.dto.SeatDTO;
import com.springboot.pople.dto.TheaterDTO;
import com.springboot.pople.dto.movie.MovieFormDTO;
import com.springboot.pople.dto.movieschedule.MovieScheduleDTO;
import com.springboot.pople.dto.movieschedule.MovieScheduleFormDTO;
import com.springboot.pople.dto.theater.TheaterFormDTO;
import com.springboot.pople.entity.Users;
import com.springboot.pople.repository.UsersRepository;
import com.springboot.pople.repository.movie.MovieRepository;
import com.springboot.pople.service.cinema.CinemaService;
import com.springboot.pople.service.movie.MovieService;
import com.springboot.pople.service.movie.MovieService2;
import com.springboot.pople.service.movieSchedule.MovieScheduleService;
import com.springboot.pople.service.seat.SeatService;
import com.springboot.pople.service.theater.TheaterFormService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final MovieScheduleService movieScheduleService;
    private final CinemaService cinemaService;
    private final MovieService movieService;
    private final MovieService2 movieService2;
    private final MovieRepository movieRepository;
    private final SeatService seatService;
    private final TheaterFormService theaterFormService;
    private final UsersRepository usersRepository;



    @GetMapping(value = "/form")
    public String getSchedule(Model model){
        List<MovieDTO> movieDTO = movieService.AllList();
        log.info("fsdfsdfsdfsdfsd"+movieDTO);
        model.addAttribute("movie",movieDTO);


        List<CinemaDTO> cinemaDTO =  cinemaService.AllList();
        log.info("fsdfsdfsdfsdfsd"+cinemaDTO);
        model.addAttribute("cinema",cinemaDTO);


        List<TheaterFormDTO> formDTOList  =theaterFormService.AllList();
        model.addAttribute("theater",formDTOList);


        return "views/scheduleForm";
    }

    @ResponseBody
    @RequestMapping(value = "/form",method = {RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public String postSchedule(@RequestBody HashMap<String,Object> map, Model model, HttpServletResponse resp, HttpServletRequest req , BindingResult bindingResult)  throws BindException {

        String cinemaID = (String) map.get("cinemaid");
        String theaterID = (String) map.get("theaterid");
        String movieName = (String) map.get("movieName");
        String dayTime = (String) map.get("dayTime");
        String startTime = (String) map.get("startTime");
        String endTime = (String) map.get("endTime");


        Long cinemaid = Long.parseLong(cinemaID);
        Long theaterid = Long.parseLong(theaterID);



        MovieDTO movieDTO =  movieService.nameOne(movieName);
        log.info("fsdfsdfsd"+movieDTO);


        log.info(cinemaid);
        log.info(theaterid);
        log.info(movieName);
        log.info(dayTime);
        log.info(startTime);
        log.info(endTime);

        MovieScheduleDTO scheduleDTO = MovieScheduleDTO.builder()
                .cinemaid(cinemaid)
                .theaterid(theaterid)
                .movieid(movieDTO.getMovieid())
                .dayTime(dayTime)
                .startTime(startTime)
                .endTime(endTime)
                .scheduleDate(LocalDateTime.now())
                .build();
        log.info("테스트좀 합시다 "+scheduleDTO);

        movieScheduleService.register(scheduleDTO);





        return  "";
    }

    @GetMapping(value = "/find/{movieName}")
    public String getss(@PathVariable("movieName") String movieName, Model model){

        MovieDTO movieDTO = movieService.nameOne(movieName);
        log.info("fsdfsdfsdfsdfsd"+movieDTO);

        MovieFormDTO movieFormDTO =  movieService2.getMovieDtl(movieDTO.getMovieid());
        log.info("fsdfsdfsdfsdfsdss"+movieFormDTO);
        model.addAttribute("movie",movieFormDTO);
        log.info("예매 가즈아 ㄱㄱㄱㄱ");
        List<CinemaDTO> cinemaDTO =  cinemaService.AllList();
        log.info(cinemaDTO);
        model.addAttribute("cinema",cinemaDTO);
        List<SeatDTO> seatDTO = seatService.AllList();
        log.info(seatDTO);
        model.addAttribute("seat",seatDTO);

        List<TheaterFormDTO> formDTOList  =theaterFormService.AllList();
        log.info("1231231231231"+formDTOList);
        model.addAttribute("theater",formDTOList);
        List<MovieScheduleDTO> movieScheduleDTOList =movieScheduleService.MovieList(movieDTO.getMovieid());
        log.info("1231231231231fsdfsdfsdfsdfsdfsdfsd"+movieScheduleDTOList);
        model.addAttribute("schedule",movieScheduleDTOList);


        return "views/schedule";
    }





    @ResponseBody
    @RequestMapping(value = "/cinemaid",method = {RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public CinemaDTO postcinemaid(@RequestBody HashMap<String,Object> map, Model model, HttpServletResponse resp, HttpServletRequest req , BindingResult bindingResult)  throws BindException {

        String cinemaID = (String) map.get("cinemaid");

        Long cinemaid = Long.parseLong(cinemaID);

        log.info(cinemaid);

        CinemaDTO scheduleDTO = CinemaDTO.builder()
                .cinemaid(cinemaid)
                .build();

        CinemaDTO cinemaDTO =cinemaService.readOne(scheduleDTO.getCinemaid());

        log.info("테스트갑좀 나와라 "+cinemaDTO);

        return cinemaDTO;
    }

    @ResponseBody
    @RequestMapping(value = "/theaterid",method = {RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public List<SeatDTO> posttheaterid(@RequestBody HashMap<String,Object> map, Model model, HttpServletResponse resp, HttpServletRequest req , BindingResult bindingResult)  throws BindException {

        String theaterID = (String) map.get("theaterid");

        Long theaterid = Long.parseLong(theaterID);

        log.info(theaterid);

        TheaterDTO theaterDTO = TheaterDTO.builder()
                .id(theaterid)
                .build();
        List<SeatDTO> theaterDTOList= seatService.theaterList(theaterDTO.getId());

        log.info("dsdasdasdasd"+theaterDTOList);

//        List<SeatDTO> seatDTO =seatService.AllList(theaterDTO.getId());/

//        log.info("테스트갑좀 나와라 "+seatDTO);

        return theaterDTOList;
    }




    }
