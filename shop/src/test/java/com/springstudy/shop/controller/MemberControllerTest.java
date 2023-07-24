package com.springstudy.shop.controller;

import com.springstudy.shop.dto.MemberDTO;
import com.springstudy.shop.entity.Member;
import com.springstudy.shop.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Log4j2
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class MemberControllerTest {
    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    public Member createMember(){
        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setEmail("admin");
        memberDTO.setName("관리자");
        memberDTO.setAddress("1234");
        memberDTO.setPassword("1234");

        // dto -> entity, 암호화 적용
        return Member.createMember(memberDTO, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest(){

        Member member = createMember();
        Member savedMember =  memberService.saveMember(member);

        // assertEquals메소드를 이용하여 저장하려고 요청했던 값과 실제 저장된 데이를 비교
        // assertEquals(기대값, 실제값);
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getRole(), savedMember.getRole());

    }


    @Test@DisplayName("중복 회원 테스트-이메일 기준")
    public void saveDuplicateMemberTest(){
        Member memberTest1 = createMember();
        Member memberTest2 = createMember();

        memberService.saveMember(memberTest1);

        Throwable e = assertThrows(IllegalStateException.class, () ->{
            memberService.saveMember(memberTest2);
        });

        assertEquals("이미 가입된 회원입니다.",e.getMessage());
    }
    
    
    //로그인 로그아웃 테스트
    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception{
        String email = "test@email.com";
        String password ="1234";

        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/members/login")
                .user(email)
                .password(password)
                ).andExpect(SecurityMockMvcResultMatchers.authenticated());

    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception{
        String email = "test@gmail.com";
        String password = "1111";

        //this.createMember(email,password);

        mockMvc.perform(
                formLogin().userParameter("email")
                        .loginProcessingUrl("/members/login")
                        .user(email)
                        .password(password)
        ).andExpect(SecurityMockMvcResultMatchers.unauthenticated());


    }

    //상품 등록 페이지 구너한 테스트
    @Test
    @WithMockUser(username = "admin",roles = "ADMIN")
    public void itemFormTest() throws Exception{
        //this.createMember();
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))
                .andDo(print())
                .andExpect(status().isOk()); //기대값은 상태코드 200

    }

    @Test
    @DisplayName("상품 등록 페이지 일반 회원접근 테스트")
    @WithMockUser(username = "user",roles = "USER")
    public void itemFormNotAdminTest() throws Exception{
        //this.createMember();
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))
                .andDo(print())
                .andExpect(status().isOk()); //기대값은 상태코드 403 : 권한에 대한 거절오류

    }


}
