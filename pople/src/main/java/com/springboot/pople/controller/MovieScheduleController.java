package com.springboot.pople.controller;

import com.springboot.pople.dto.CinemaDTO;
import com.springboot.pople.dto.MovieDTO;
import com.springboot.pople.dto.SeatDTO;
import com.springboot.pople.dto.movie.MovieFormDTO;
import com.springboot.pople.dto.theater.TheaterFormDTO;
import com.springboot.pople.service.cinema.CinemaService;
import com.springboot.pople.service.movie.MovieService;
import com.springboot.pople.service.movieSchedule.MovieScheduleService;
import com.springboot.pople.service.theater.TheaterService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("/Schedule")
@RequiredArgsConstructor
public class MovieScheduleController {

    private final MovieScheduleService movieScheduleService;
    private final CinemaService cinemaService;
    private final MovieService movieService;
    private final TheaterService theaterService;

    @GetMapping(value = "form")
    public String getSchedule(Model model){
        return "";
    }



}
