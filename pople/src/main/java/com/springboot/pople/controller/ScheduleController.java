package com.springboot.pople.controller;

import com.springboot.pople.dto.CinemaDTO;
import com.springboot.pople.dto.SeatDTO;
import com.springboot.pople.service.cinema.CinemaService;
import com.springboot.pople.service.movie.MovieService;
import com.springboot.pople.service.movieSchedule.MovieScheduleService;
import com.springboot.pople.service.seat.SeatService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final MovieScheduleService movieScheduleService;
    private final CinemaService cinemaService;
    private final MovieService movieService;
    private final SeatService seatService;

    @GetMapping(value = "/form")
    public String getSchedule(Model model){

        return  "";
    }
    @PostMapping(value = "/form")
    public String postSchedule(Model model){

        return  "";
    }

    @GetMapping(value = "/find")
    public String getFind(Model model){
        log.info("예매 가즈아 ㄱㄱㄱㄱ");
        List<CinemaDTO> cinemaDTO =  cinemaService.AllList();
        log.info(cinemaDTO);
        model.addAttribute("cinema",cinemaDTO);
        List<SeatDTO> seatDTO = seatService.AllList();
        log.info(seatDTO);
        model.addAttribute("seat",seatDTO);



        return "views/schedule";
    }







}
