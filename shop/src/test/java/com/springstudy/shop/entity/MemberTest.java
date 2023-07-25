package com.springstudy.shop.entity;

import com.springstudy.shop.dto.MemberDTO;
import com.springstudy.shop.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Log4j2
@Transactional // 테스트용 , lollback 자동 적용
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;
    public Member createMember(){
        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setEmail("ccc@email.com");
        memberDTO.setName(" 클라리넷");
        memberDTO.setAddress("양산시");
        memberDTO.setPassword("1234");

        // dto -> entity, 암호화 적용
        return Member.createMember(memberDTO, passwordEncoder);
    }

    @Test
    @DisplayName("Auditing 테스트 ")
    @WithMockUser(username = "gildong",roles = "USER")
    public void auditingTest(){
        Member newmember = new Member();
//        newmember = this.createMember();
        memberRepository.save(newmember);
        em.flush(); em.clear();

        Member member =  memberRepository.findById(newmember.getId()).orElseThrow(EntityNotFoundException::new);

        log.info("--------------");
        log.info("=> register time" +member.getRegDate());
        log.info("=> update time" +member.getModDate());
        log.info("=> modifindBy time" +member.getModifindBy());
        log.info("=> cerateBy time" +member.getCerateBy());


    }


}
