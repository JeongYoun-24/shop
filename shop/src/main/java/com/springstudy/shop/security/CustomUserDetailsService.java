package com.springstudy.shop.security;

import com.springstudy.shop.entity.Member;
import com.springstudy.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
//@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final   MemberRepository memberRepository;
//    private  PasswordEncoder passwordEncoder;
//    public   CustomUserDetailsService(){
//        this.passwordEncoder = new BCryptPasswordEncoder();
//
//    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if(member == null){  //회원 정보가 없다
            throw new UsernameNotFoundException(email);
        }

        //db로 회원 정보
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
//                .authorities("ROLE"+member.getRole().toString())
                .build();
    }








}
