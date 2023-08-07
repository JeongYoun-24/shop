package com.springboot.pople.service;

import com.springboot.pople.dto.UsersDTO;
import com.springboot.pople.entity.Users;
import com.springboot.pople.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class usersServiceImpl implements UsersService{


    private final ModelMapper modelMapper;

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;


    public Users saveUsers(Users users){
        // 서버에서 validate적용
        validateDuplicateMember(users);
        return usersRepository.save(users);
    }

    private void validateDuplicateMember(Users users){
        Users findMember = usersRepository.findByEmail(users.getEmail());
        Users findMember2 = usersRepository.findByNameOrEmail(users.getName() ,users.getEmail());

        // 이미 가입된 회원인 경우 예외 발생
        if (findMember2 != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

    }







    @Override
    public String register(UsersDTO usersDTO) {
        // dto-> entity로 데이터 복사
        Users board = modelMapper.map(usersDTO,Users.class);
        String user_id = usersRepository.save(board).getUserid();

        return user_id;
    }

    @Override
    public UsersDTO readOne(String user_id) {
        Optional<Users> result = usersRepository.findById(user_id);
        Users users = result.orElseThrow();
        UsersDTO usersDTO = modelMapper.map(users,UsersDTO.class);

        return usersDTO;
    }

    @Override
    public void modify(UsersDTO usersDTO) {

        Optional<Users> result =  usersRepository.findById(usersDTO.getUserid());
        Users board = result.orElseThrow();

        board.change(usersDTO.getPassword(),usersDTO.getName(),usersDTO.getEmail());
        usersRepository.save(board);

    }





    @Override
    public void remove(String user_id) {
        usersRepository.deleteById(user_id);
    }

    @Override
    public UsersDTO login(String name, String email) {
        Users users = usersRepository.findByNameOrEmail(name,email);

        UsersDTO usersDTO = modelMapper.map(users,UsersDTO.class);

        return usersDTO;
    }

    @Override
    public UsersDTO loginId(String email) {

        Users users = usersRepository.findByEmail(email);

        UsersDTO usersDTO = modelMapper.map(users,UsersDTO.class);

        return usersDTO;
    }

    @Override
    public UsersDTO loginPwd(String userid, String email) {
        Users users = usersRepository.findByUseridOrEmail(userid,email);

        UsersDTO usersDTO = modelMapper.map(users,UsersDTO.class);

        return usersDTO;
    }

    @Override
    public void pwdUpdate(UsersDTO usersDTO) {

        Optional<Users> result =  usersRepository.findById(usersDTO.getUserid());
        Users users = result.orElseThrow();

        users.pwdUpdate(usersDTO.getPassword(),passwordEncoder);
        usersRepository.save(users);


    }

//    @Override
//    public UsersDTO findByLoginId(String user_id, String user_pwd) {
//        return null;
//    }
//
//    @Override
//    public List<Users> allList() {
//        List<Users> itemList = usersRepository.findAll();
//
//
//        return itemList;
//    }

//    @Override
//    public UsersDTO loginId(String user_email) { // 이메일로 비밀번호 찾기
//        Users users = usersRepository.findByuser_email(user_email);
//        ;
//        UsersDTO usersDTO = modelMapper.map(users,UsersDTO.class);
//
//        return usersDTO;
//    }

//    @Override
//    public UsersDTO loginPwd(String user_id, String user_email) { //아이디와 이메일로 비밀번호 찾기
//        Optional<Users> result = usersRepository.findById(user_id);
//        Users users = result.orElseThrow();
//        UsersDTO usersDTO = modelMapper.map(users,UsersDTO.class);
//
//        return null;
//    }

//    @Override
//    public UsersDTO selectOne(String account) {
//        return mapper.selectOne(account);
//    }
//
//    @Override
//    public void keepLogin(String session, Date limitTime, String account) {
//        Map<String, Object> datas = new HashMap<>();
//        datas.put("sessionId", session);
//        datas.put("limitTime", limitTime);
//        datas.put("account", account);
//
//        mapper.keepLogin(datas);
//    }


}
