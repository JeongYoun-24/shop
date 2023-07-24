package com.springstudy.shop.controller;

import com.springstudy.shop.dto.MemberDTO;
import com.springstudy.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value="/register")
    public String memberForm(Model model){

//        memberService.saveMember()

        model.addAttribute("memberDTO", new MemberDTO());

        return "member/registerMember";
    }

    @GetMapping("/login")
    public String loginGET(String error, String logout){
        log.info("logout =  "+logout);
//        if(logout != null){
//            log.info("user logout");
//        }

        return "member/login";
    }
    @GetMapping("/logout")
    public String logoutGet(){

        return "redirect :/member/login?logout";
    }


    @GetMapping("/login/error")
    public String loginerror(Model model){

        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호가 잘못됏슴다");
        return "member/login";
    }



}
