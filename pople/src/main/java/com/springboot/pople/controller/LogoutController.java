package com.springboot.pople.controller;

import com.springboot.pople.sesstion.SessionConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogoutController {
    @Autowired
    private SessionConst sessionConst;
//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request){
//        sessionConst.expire(request);
//
//        return "views/main";
//    }



}
