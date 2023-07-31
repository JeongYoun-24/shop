package com.springboot.pople.security;

import com.springboot.pople.entity.Users;
import com.springboot.pople.repository.MemberRepository;
import com.springboot.pople.repository.UsersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
//@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final MemberRepository memberRepository;
    private final UsersRepository usersRepository;

//    private  PasswordEncoder passwordEncoder;
//    public   CustomUserDetailsService(){
//        this.passwordEncoder = new BCryptPasswordEncoder();
//
//    }



    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//        Users users = usersRepository.findByEmail(Email);
        Users users = usersRepository.findByUserid(userId);

        if(users == null){  //회원 정보가 없다
            throw new UsernameNotFoundException(userId);
        }

        //db로 회원 정보
        return User.builder()
                .username(users.getName())
                .password(users.getPassword())
                .roles(users.getRole().toString())
//                .authorities("ROLE"+member.getRole().toString())
                .build();
    }








}
