package com.springstudy.shop.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    //현재 로그인한 사용ㅈ의 정보를 등록자와 수정자로 지정
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = "";
        if(authentication != null){
            //현재 로그인한 사용ㅈ의 정보를 조회하여  사용자의 이름을 등록자 와 수정자로 지정
            userId = authentication.getName();
        }

        return Optional.of(userId);
    }
}


/*
Spring Data Jpa : Auditing 기능을 제공 => 엔티티가 저장 또는 수정될때 자동을 등록일 , 수정일 , 등록자.수정자를 입력
공통 멤버 변수들을 추상클래스로 만들고 , 상속 받는 형태로 엔티티를 리펙토링

현재 로그인한 사용ㅈ의 정보를 등록자와 수정자로 지정 : AuditorAware인터페이스를 구현한 클래스 작성


*/




