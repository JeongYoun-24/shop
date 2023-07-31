package com.springboot.pople.service;

import com.springboot.pople.dto.UsersDTO;
import com.springboot.pople.entity.Users;

import java.util.List;

public interface UsersService {

    public String register(UsersDTO usersDTO);
    public UsersDTO readOne(String user_id);
    public void modify(UsersDTO usersDTO);
    public void remove(String user_id);


    public UsersDTO login(String name , String email);
    public UsersDTO loginId(String email);

    public UsersDTO loginPwd(String userid,String email);

    public void pwdUpdate(UsersDTO usersDTO);


    public Users saveUsers (Users users);


//    public UsersDTO findByLoginId(String user_id,String user_pwd);
//
//    public List<Users> allList(); // 전체 리스트

//    public UsersDTO loginId(String user_email); // 이메일정보로 아이디 찾기
//    public UsersDTO loginPwd(String user_id,String user_email); // 아이디 이메일정보로 비밀번호 찾기

}
