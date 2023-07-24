package com.springstudy.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MainController {



//    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(value="/")
    public String main(){
        return "index";
    }



}
