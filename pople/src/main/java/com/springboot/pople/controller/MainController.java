package com.springboot.pople.controller;

import com.springboot.pople.dto.UsersDTO;
import com.springboot.pople.sesstion.SessionConst;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@Log4j2
@RequestMapping("/*")
public class MainController{

    @Autowired
    private SessionConst sessionConst;


        @GetMapping(value = "/main") // HashMap<String, Object> map
        public String main(HttpServletRequest request,Model model) {




            return "views/main";
        }
    @GetMapping(value = "index")
    public void indexGET(@SessionAttribute(name = "loginUser", required = false)UsersDTO usersDTO ,Model model) {
        log.info("Controller indexGET");

        model.addAttribute("loginUser", usersDTO);
    }



}
