package com.springboot.pople.restcontroller;

import com.springboot.pople.dto.CinemaDTO;

import com.springboot.pople.service.cinema.CinemaService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/cinemass")
public class ScheduleRestController {

    private final CinemaService cinemaService;
    @ApiOperation(value ="Replies of Board", notes="GET방식으로 특정 게시글에 대한 댓글 목록")// swagger-ui적용시 사용
    @GetMapping(value="/list/{cinemaid}")
    public List<CinemaDTO> getList(@PathVariable("cinemaid") long cinemaid){
        log.info("ㅇㅁㄴㅇㅁㄴㅇㅁㄴ");


        List<CinemaDTO> cinemaDTOList = cinemaService.AllList();



        return cinemaDTOList;
    }





}
