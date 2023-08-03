package com.springboot.pople.controller;

import com.springboot.pople.dto.UsersDTO;
import com.springboot.pople.dto.movie.MainMovieDTO;
import com.springboot.pople.dto.movie.MovieSearchDTO;
import com.springboot.pople.service.movie.MovieService2;
import com.springboot.pople.sesstion.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Member;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Optional;

@Controller
@Log4j2
@RequestMapping("/*")
@RequiredArgsConstructor
public class MainController{

    @Autowired
    private SessionConst sessionConst;

    private final MovieService2 movieService2;

        @GetMapping(value = "/main") // HashMap<String, Object> map
        public String main(Model model,MovieSearchDTO movieSearchDTO, Optional<Integer> page) {
            //페이징 설정
            Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 ,4);

            Page<MainMovieDTO> movies = movieService2.getMainItemPage(movieSearchDTO,pageable);
            model.addAttribute("movies",movies);
            model.addAttribute("movieSearchDTO",movieSearchDTO);
            model.addAttribute("maxPage",4);

            return "views/main";
        }
    @GetMapping(value = "index")
    public void indexGET(@SessionAttribute(name = "loginUser", required = false)UsersDTO usersDTO ,Model model) {
        log.info("Controller indexGET");





        model.addAttribute("loginUser", usersDTO);
    }



}
