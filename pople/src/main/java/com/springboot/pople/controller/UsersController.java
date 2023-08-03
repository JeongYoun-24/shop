package com.springboot.pople.controller;

import com.springboot.pople.dto.UsersDTO;
import com.springboot.pople.entity.Users;
import com.springboot.pople.service.LoginService;
import com.springboot.pople.service.UsersService;
import com.springboot.pople.service.users.UsersJoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    @Autowired
    private UsersService usersService;

    private final UsersJoinService usersJoinService;
    private final PasswordEncoder passwordEncoder;
    private final LoginService loginService;


    @GetMapping("/login")
    public String loginGET(String error, String logout){
        log.info("logout =  "+logout);
//        if(logout != null){
//            log.info("user logout");
//        }

        return "users/login";
    }
    @GetMapping("/logout")
    public String logoutGet(){

        return "redirect :/users/login?logout";
    }


    @GetMapping("/login/error")
    public String loginerror(Model model){

        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호가 잘못됏슴다");
        return "users/login";
    }

    @GetMapping(value = "/join")  // 회원 가입 페이지
    public String joinpage(Model model){
        model.addAttribute("usersDTO", new UsersDTO());

        return "users/join";
    }

    @PostMapping(value="/join")
    public String saveMember(@Valid UsersDTO usersDTO, BindingResult bindingResult, Model model){
        log.info("--- member post");

        if (bindingResult.hasErrors()){
            return "users/join";
        }
        log.info(usersDTO);


        try {
            Users users = Users.createMember(usersDTO, passwordEncoder);
            log.info(users);

            usersJoinService.saveUsers(users);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "users/join";
        }


        return "redirect:/users/login";
    }






    @GetMapping("/modify")
    public String modifypage(Model model){



        return "users/modify";
    }

    @PostMapping("/modify")
    public String modifyDate(Model model){



        return "views/main";
    }

    @GetMapping("/loginId")
    public String loginId(Model model){



        return "users/loginId";
    }
//    @PostMapping("/users/loginId" produces="application/json;charset=UTF-8")
    @ResponseBody
    @RequestMapping(value = "/loginId",method = {RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public String loginIdData(@RequestBody HashMap<String, Object> map, Model model, HttpServletResponse resp, HttpServletRequest req , BindingResult bindingResult)  throws BindException{
        String rt  = "";

        String name = (String) map.get("name");
        String email = (String) map.get("email");

        log.info(name);
        log.info(email);

        if (bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        if(name.length() == 0 && email.length() == 0){
            rt  = "실패";

        }else {
       UsersDTO usersDTO =  usersService.login(name,email);
        log.info(usersDTO);
        if(usersDTO.getName().equals(name)){
            log.info("이름 일치");
            if (usersDTO.getEmail().equals(email)){
                log.info("이메일 일치");
                rt = usersDTO.getUserid();
            }else {
                log.info("이메일 불일치");
                rt = "실패";
            }

        }else{
            log.info("이름 불일치");
            rt = "실패";
        }





        }



        return rt;
    }

    @ResponseBody
    @RequestMapping(value = "/loginPwd",method = {RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public String loginPwdData(@RequestBody HashMap<String, Object> map, Model model, HttpServletResponse resp, HttpServletRequest req , BindingResult bindingResult)  throws BindException{
        String rt  = "";

        String userid = (String) map.get("userid");
        String email2 = (String) map.get("email2");

        log.info(userid);
        log.info(email2);

        if (bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        if(userid.length() == 0 && email2.length() == 0){
            rt  = "실패";

        }else {
            UsersDTO usersDTO =  usersService.loginPwd(userid,email2);
            log.info(usersDTO);
            log.info(usersDTO.getUserid());
            log.info(userid);
            if(usersDTO.getUserid().equals(userid) ){
                log.info("아이디 일치 ");
                if(usersDTO.getEmail().equals(email2) ){
                    log.info("이메일 일치");
                    rt ="일치";
                    return rt;
                }else {
                    log.info("이메일 불일치");
                    rt = "실패";
                }


            }else {
                log.info("아이디 불일치");
                rt = "실패";
            }


        }



        return rt;
    }
    @GetMapping("/pwd")
    public String pwd(Model model,String email){
        log.info(email);
    model.addAttribute("email",email);

        return "users/userpwd";
    }
    @ResponseBody
    @RequestMapping(value = "/pwd",method = {RequestMethod.POST}, produces="application/json;charset=UTF-8")
    public String pwddate(@RequestBody HashMap<String, Object> map, Model model, HttpServletResponse resp, HttpServletRequest req , BindingResult bindingResult)  throws BindException{
        String rt = "";
        log.info("비밀번호 변경 요청 ");
        String email = (String) map.get("email");
        String password = (String) map.get("password");
        log.info(password);
        log.info(email);

        UsersDTO usersDTO =  usersService.loginId(email);

//        Users users = Users.(usersDTO, passwordEncoder);
//        usersJoinService.saveUsers(users);

        log.info(usersDTO);

        usersDTO.setPassword(password);

        usersService.pwdUpdate(usersDTO);



        rt = "성공";




        return rt;
    }




}
