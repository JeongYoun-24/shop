package com.springboot.pople.controller.cinema;

import com.springboot.pople.dto.CinemaDTO;
import com.springboot.pople.dto.TheaterDTO;
import com.springboot.pople.dto.theater.TheaterFormDTO;
import com.springboot.pople.service.cinema.CinemaService;
import com.springboot.pople.service.theater.TheaterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @ResponseBody
    @RequestMapping(value = "/cinemaFind",method = {RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public   List<TheaterDTO> postAjax(@RequestBody HashMap<String,Object> map, Model model, HttpServletResponse resp, HttpServletRequest req , BindingResult bindingResult)  throws BindException{
        log.info("데이터 요청받음 ㄱㄱ");
//        List<Map<Long,Object>>
//        @ResponseBody ResponseEntity


        String cinemaid = (String) map.get("cinemaid");
//        String cinema =  (String) map.get("cinemaid");

        Long cinemaId = (Long.parseLong(cinemaid));
       log.info(cinemaid);
        List<TheaterDTO> theaterDTO= theaterService.theaterList(cinemaId);
//        log.info("dsfdsfdsfdsfdf"+theaterDTO);


//        List<TheaterFormDTO> theaterDTO= theaterService.theaterList(cinemaid);

        List<Map<Long ,Object>> list = new ArrayList<Map<Long,Object>>();
        HashMap<Long, Object> r = new HashMap <Long,Object>();
//        List<TheaterFormDTO> theaterDTO= theaterService.theaterList(cinemaid);
//        log.info("dsfdsfdsfdsfdf"+theaterDTO);
//        HashMap<Long, Object> result = new HashMap <Long,Object>();
//
//        for(TheaterFormDTO listDTO : theaterDTO){
//            Long cinemaId = listDTO.getCinema().getCinemaid();
//            result.put(cinemaId,theaterService.theaterList(cinemaId));
//            list.add(result);
//        }

//        for(TheaterDTO listDTO : theaterDTO){
//           Long cinemaId = listDTO.getCinemaid();
//            Map<Long,Object> mapDTO =new HashMap<Long,Object>();
//            mapDTO.put(cinemaId,theaterService.theaterList(1L));
//
//            list.add(mapDTO);
//        }

//        log.info("dsfdsfdsfdsfdf"+result);
        log.info(theaterDTO);
//

        TheaterDTO theater =new TheaterDTO();


        HashMap<String, Object> result = new HashMap<>();

//        model.addAttribute("theaterList",theaterDTO);
//        new ResponseEntity<>(theaterDTO, HttpStatus.OK)
        return theaterDTO;
    }

//    @ResponseBody
//    @RequestMapping(value = "/cinemaFind",method = {RequestMethod.POST}, produces="application/json;charset=UTF-8")
//    public List<TheaterDTO> postAjax(@RequestBody HashMap<String,Long> map, Model model, HttpServletResponse resp, HttpServletRequest req , BindingResult bindingResult)  throws BindException {
//
//
//    }

}
