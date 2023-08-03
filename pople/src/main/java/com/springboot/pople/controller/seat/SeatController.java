package com.springboot.pople.controller.seat;

import com.springboot.pople.dto.CinemaDTO;
import com.springboot.pople.dto.SeatDTO;
import com.springboot.pople.dto.TheaterDTO;
import com.springboot.pople.service.cinema.CinemaService;
import com.springboot.pople.service.movie.MovieService;
import com.springboot.pople.service.seat.SeatService;
import com.springboot.pople.service.theater.TheaterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/seat")
public class SeatController {

    private final SeatService seatService;
    private final CinemaService cinemaService;
    private final TheaterService theaterService;

    @GetMapping("/form")
    public String getSeat(Model model){
        List<CinemaDTO> cinemaDTO =  cinemaService.AllList();
        log.info(cinemaDTO);
        model.addAttribute("cinema",cinemaDTO);
        List<TheaterDTO> theaterDTOList= theaterService.AllList();
        log.info("값이 안나와 ;;; "+theaterDTOList);
        model.addAttribute("theater",theaterDTOList);

//        TheaterDTO theaterDTO =   theaterService.readOne();
//        log.info(theaterDTO);




        return "form/seatform";
    }
    @PostMapping("/form")
    public String postSeat(Model model, HttpServletRequest req){
        log.info("좌석 등록 데이터 요청 ㄱㄱ ");

        int seatNo = Integer.parseInt(req.getParameter("seatNo"));
        int seatGroup = Integer.parseInt(req.getParameter("seatGroup"));
        String seatLineNo = req.getParameter("seatLineNo");
        Long cinemaid = (Long.parseLong(req.getParameter("cinemaid")));
        Long theaterid = (Long.parseLong(req.getParameter("theaterid")));

        log.info(seatNo);
        log.info(seatGroup);
        log.info(seatLineNo);
        log.info(cinemaid);
        log.info(theaterid);

        SeatDTO seatDTO =SeatDTO.builder()
                .seatNo(seatNo)
                .seatGroup(seatGroup)
                .seatLineNo(seatLineNo)
                .cinemaid(cinemaid)
                .theaterid(theaterid)
                .build();
            log.info(seatDTO);

     Long result =  seatService.register(seatDTO);

        log.info(result);



        return  "redirect:/seat/form";
    }


}
