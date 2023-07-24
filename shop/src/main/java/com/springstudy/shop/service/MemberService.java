package com.springstudy.shop.service;

import com.springstudy.shop.entity.Member;
import com.springstudy.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService  {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member){
        // 서버에서 validate적용
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail());

        // 이미 가입된 회원인 경우 예외 발생
        if (findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

    }



 //   @Override
//    public UserDetails loadUserByUsername(String Email) throws UsernameNotFoundException {
//        Member member = memberRepository.findByEmail(Email);
//
//        if(member == null){  //회원 정보가 없다
//            throw new UsernameNotFoundException(Email);
//        }
//
//        //db로 회원 정보
//        return User.builder()
//                .username(member.getEmail())
//                .password(member.getPassword())
//                .roles(member.getRole().toString())
//                .build();
//    }



}
/*
userdetailsservice 인터페이스: db에서 회우너 정보를 가져오는 역할
loaduserbyusername () : 회원 정보를 조회하여 사용자의 정보와 권한을 갖는 userdetails 인터페이스 반환
userdetail : 직접 구현 , 시큐리티에서 제공하는 user 클래스 사용

 */
