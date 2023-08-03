package com.springboot.pople.controller.cinema;

import com.springboot.pople.dto.CinemaDTO;
import com.springboot.pople.dto.TheaterDTO;
import com.springboot.pople.service.cinema.CinemaService;
import com.springboot.pople.service.theater.TheaterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/theater")
@RequiredArgsConstructor
public class TheaterController {
    private final TheaterService theaterService;
    private final CinemaService cinemaService;
    @GetMapping(value = "/form")
    public String getTheater(Model model){
        List<CinemaDTO> cinemaDTOList = cinemaService.AllList();
        log.info("상영관 등록페이지 요청 ㄱㄱ");
        model.addAttribute("cinema",cinemaDTOList);


        return  "cinema/theater";
    }
    @PostMapping(value = "/form")
    public String postTheater(Model model, HttpServletRequest req){
        log.info("상영관 등록 요청 ㄱㄱ");
        String theater = req.getParameter("theaterid");
//        String cinemaid = req.getParameter("cinemaid");
        log.info(theater);
//        log.info(cinemaid);

        Long cinemaid = (Long.parseLong(req.getParameter("cinemaid")));
        TheaterDTO theaterDTO = new TheaterDTO();
        theaterDTO.setCinemaid(cinemaid);
        log.info(theaterDTO);


       Long theaterid = theaterService.register(theaterDTO);
        log.info(theaterid);



        return  "redirect:/theater/form";
    }


}
