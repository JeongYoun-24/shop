package com.springboot.pople.controller;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.springboot.pople.dto.UsersDTO;
import com.springboot.pople.entity.Users;
import com.springboot.pople.service.UsersService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@Log4j2
@RequestMapping("/usersssssss")
public class TestController {

    @Autowired
    private UsersService usersService;
    @GetMapping(value = "/layout")
    public String layout(){

        return "layout/layout";
    }



    @GetMapping(value = "/users/loginpage") // 로그인 페이지
    public String loginpage(){

        return "users/login";
    }
    @GetMapping(value = "/users/join")  // 회원 가입 페이지
    public String joinpage(){


        return "users/join";
    }
    @RequestMapping(value = "/users/joindata",method = {RequestMethod.POST}) // 회원가입 데이터
    public String joindata(@Valid UsersDTO usersDTO,BindingResult bindingResult,RedirectAttributes redirectAttributes ){
//        String users_id = req.getParameter("user_id");
//        String user_pwd = req.getParameter("user_pwd");
//        String user_name = req.getParameter("user_name");
//        String user_email = req.getParameter("user_email");
//        String phone = req.getParameter("phone");
//        String birthdate = req.getParameter("birthdate");
//        log.info(users_id);
//        log.info(user_pwd);
//        log.info(user_name);
//        log.info(user_email);
//        log.info(phone);
//        log.info(birthdate);
//
//
//
//        UsersDTO usersDTO = UsersDTO.builder()
//                .user_id(users_id)
//                .user_pwd(user_pwd)
//                .user_name(user_name)
//                .user_email(user_email)
//                .phone(phone)
//                .birthdate(birthdate)
//                .build();

//        String user_id = usersService.register(usersDTO);


        if(bindingResult.hasErrors()){
            log.info("board errors ...");
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors()); //리다이렉션 시 담을 값
            return "redirect: /users/join";
        }

        String user_id = usersService.register(usersDTO);
        redirectAttributes.addFlashAttribute("result",user_id);

        return "/users/login";  //sendRedirect

//        return "users/login";
    }




    @ResponseBody
    @RequestMapping(value = "/login",method = {RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public String login(@RequestBody HashMap<String, Object> map, Model model,HttpServletResponse resp,HttpServletRequest req){
        String user_id = (String) map.get("user_id");
        String user_pwd = (String) map.get("user_pwd");
        String login_auto = (String) map.get("auto");

        log.info(user_id);
        log.info(user_pwd);
        log.info(login_auto);
        boolean rememberMe = login_auto != null && login_auto.equals("on");
        if(rememberMe) {
            //랜덤 아이디 값 얻기
            String uuid = UUID.randomUUID().toString();
            System.out.println(uuid);

            /*  service.updateUuid(user_id, uuid);*/

//            member.setUuid(uuid);

            //---------------------

            Cookie rememberCookie = new Cookie("remember-me", uuid);
            rememberCookie.setMaxAge(24*60*60);
            rememberCookie.setPath("/");

            resp.addCookie(rememberCookie);



        }

        String rt= "";
        int isOk=0;
        UsersDTO usersDTO = usersService.readOne(user_id);
//        List<Users> usersDTO = usersService.allList();


//        String memberId = usersDTO.getUserId();
//        String memberPwd = usersDTO.getUserPwd();
//        member = service.login(user_id);

//        String memberId = member.getUser_id();
//        String memberPwd = member.getUser_pwd();
//
//        System.out.println(member);




//        if(memberId.equals(user_id)) {
//
//            if(memberPwd.equals(user_pwd)) {
//
//                isOk = 1;
//                HttpSession session = req.getSession();
//                session.setAttribute("login", usersDTO.getUserId());
//
//            }else {
//                isOk=2;
//                System.out.println("비밀번호 틀림");
//            }
//
//
//        }else {
//            isOk= -1;
//            System.out.println("아이디 정보 없음");
//
//        }


        if(isOk ==1) {

            //pw.print("<script> alert('로그인성공');  location.href='/web01/boardlist/list.do'</script>");
            rt = "로그인성공" ;
            log.info("로그인성공");

        }else if(isOk == 2) {
            //pw.print("<script> alert('비밀번호 틀림');  location.href='/web01/login'</script>");

            rt = "비밀번호를 잘못입렸습니다." ;
            log.info("비밀번호 틀림");
        }else {
            //pw.print("<script> alert('로그인 실패');  location.href='/web01/login'</script>");


            rt = "아이디를 잘못입렸습니다." ;
        }



        return rt;
    }



}
