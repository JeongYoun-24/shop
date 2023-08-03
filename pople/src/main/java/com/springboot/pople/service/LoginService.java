package com.springboot.pople.service;

import com.springboot.pople.dto.UsersDTO;
import com.springboot.pople.entity.Users;
import com.springboot.pople.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {


    private final UsersRepository usersRepository;



    public Users saveUsers(Users users){
        // 서버에서 validate적용
        validateDuplicateMember(users);
        return usersRepository.save(users);
    }

    private void validateDuplicateMember(Users users){
//        Users findMember = usersRepository.findByEmail(users.getEmail());
        Users findMember = usersRepository.findByUserid(users.getUserid());

        // 이미 가입된 회원인 경우 예외 발생
        if (findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

    }



}
