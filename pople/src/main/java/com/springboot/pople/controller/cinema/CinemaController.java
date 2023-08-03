package com.springboot.pople.controller.cinema;

import com.springboot.pople.dto.CinemaDTO;
import com.springboot.pople.entity.Users;
import com.springboot.pople.service.cinema.CinemaService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Log4j2
@Controller
@RequestMapping("/cinema")
@RequiredArgsConstructor
public class CinemaController {

    private final CinemaService cinemaService;

    @GetMapping(value = "/form")
    public String getcinemaForm(Model model){
        log.info("영화관 등록 페이지 ㄱㄱ");


        return  "cinema/cinemaForm";
    }

    @ResponseBody
    @RequestMapping(value = "/form",method = {RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public String postcinemaForm(@RequestBody HashMap<String, Object> map, Model model, HttpServletResponse resp, HttpServletRequest req , BindingResult bindingResult)  throws BindException {
        log.info("영화관 등록 데이터 저장 요청 ㄱㄱ");
        String rt  = "";
        String cinemaName = (String) map.get("cinemaName");
        String cinemaAddrss = (String) map.get("cinemaAddrss");
        String cinemaSeatCount = (String) map.get("cinemaSeatCount");
        String xAxis = (String) map.get("xAxis");
        String yAxis = (String) map.get("yAxis");

        log.info(cinemaName);
        log.info(cinemaAddrss);
        log.info(cinemaSeatCount);
        log.info(xAxis);
        log.info(yAxis);

        if(cinemaName.length() == 0 ){
            rt = "실패";
        }else if(cinemaAddrss.length() == 0) {
            rt = "실패";
        }else if(cinemaSeatCount.length() == 0){
            rt = "실패";
        }else {
            CinemaDTO cinemaDTO = new CinemaDTO();

            cinemaDTO.setCinemaName(cinemaName);
            cinemaDTO.setCinemaAddrss(cinemaAddrss);
            cinemaDTO.setCinemaSeatCount(Integer.parseInt(cinemaSeatCount));
            cinemaDTO.setXaxis(xAxis);
            cinemaDTO.setYaxis(yAxis);
            log.info(cinemaDTO);
            Long cinemaid = cinemaService.register(cinemaDTO);
            log.info(cinemaid);
            rt = "성공";
        }




        return  rt;
    }




}
