package com.springstudy.shop.junittest;

import com.springstudy.shop.entity.Board;
import com.springstudy.shop.repository.BoardRepository;
import com.springstudy.shop.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.assertj.AssertableReactiveWebApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.List;
// 어플리케이션 을 서버에 배포하지 않고 테스트용 MVC환경 제공
// 초 및 전송 응답 기능 을 제공 , Controller, service, DAO 테스용으로 사용

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc // : MockMvc를 생성ㅎ하고 자동으로 구성하는 어노테이션
public class Test02 {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;

     // 2.
    @BeforeEach
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

    }

    @AfterEach
    public void cleanUp(){

    }

    @DisplayName("회원 조회 테스트")
    @Test
    public void getAllMembers() throws Exception {



        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.findAll(pageable);

        log.info("total count: "+result.getTotalElements());
        log.info("total pages: "+result.getTotalPages());
        log.info("page number: "+result.getNumber());
        log.info("page size: "+result.getSize());

        List<Board> boardList = result.getContent();// .getContent() :데이터 가져오는 메서드
        boardList.forEach(board -> log.info(board));



        final  String url = "/board/list";
    }

    @WithMockUser(username = "dowone@naver.com" , roles = "USER")
    @Test
    @DisplayName("ㄴㄴ")
    public void getBoard() throws Exception {

        log.info(
                mockMvc.perform(MockMvcRequestBuilders.get("/board/read")
                        .param("bno" ,"1000")
                        .param("page","1")
                        .param("size","10")
                )
                        .andDo(print()) // 응답 결과 출력 
                        .andExpect(status().isOk()) // response status 검증
                        .andExpect(model().attributeExists("dto"))


        );

    }



    @Test
    @WithMockUser(username = "dowone@naver.com" , roles = "USER")
    @DisplayName("ㄴㄴ2")
    public void getBoardList()throws  Exception{
        log.info(
                mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
                        .andDo(print()) // 응답 결과 출력
                        .andExpect(status().isOk()) // response status 검증
                        .andExpect(model().attributeExists("responseDTO"))
        );

    }






}
