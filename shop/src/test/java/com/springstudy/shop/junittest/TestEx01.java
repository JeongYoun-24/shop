package com.springstudy.shop.junittest;

import com.mysema.commons.lang.Assert;
import lombok.extern.log4j.Log4j2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@Log4j2
@SpringBootTest
public class TestEx01 {

        // 단위 테스트
        @DisplayName("단위 테스트연습01 ") // 테스트 이름을 명시
        @Test
        public void Test01(){
            // 1.given
            int a =1 ;
            int b = 2;

            int sum = 3;

            // 2. when
            int result= a+b;

            // 3. them

            // 첫번쨰인자 : 기대값 , 두번째인자 : 실제로 검증할 값
//            org.junit.jupiter.api.Assertions.assertEquals(sum,result);
//    //        Assert.assertThat(result)
//            Assertions.assertThat(result).isEqualTo(sum);

            int num = a+b*sum;
    //       log.info(num);


        }

        @DisplayName("@BeforAll -테스트 ")
        @BeforeAll
        static void beforeAll(){
            // 전체 테스트 시작하기 전에 1회 실행
            log.info("@BeforAll - static");

        }
        @DisplayName("@BeforeEach -테스트 ")
        @BeforeEach
        public void beforeEach(){
            // 테스트 케이스를 시작하기 전마다 실행
            log.info("ㅅㅄㅄㅅㅄㅂ");
        }

        @DisplayName("단위 테스트연습02 ") // 테스트 이름을 명시
        @Test
        public void Test02(){
            log.info("??????!?!!?!?!???!?!!?!?!?!?!!!!?!?!?!?!?!?!?!?!?!?!?!?!?!?!!?!!!?!!?!!?!?!?!");
            log.info("@Test02");


        }
        @Test
        @DisplayName("단위 테스트연습03 ") // 테스트 이름을 명시
        public void Test03(){
            log.info("@Test03");
        }
        @Test
        @DisplayName("단위 테스트연습04 ") // 테스트 이름을 명시
        public void Test04(){
            log.info("@Test04");
        }
        @Test
        @DisplayName("단위 테스트연습05 ") // 테스트 이름을 명시
        public void Test05(){
            log.info("@Test05");
        }

    @DisplayName("@AfterEach -테스트 ")
    @AfterEach
    public void afterEach(){
        // 테스트 케이스를 시작하기 전마다 실행
        log.info("@AfterEach");
    }
    @DisplayName("@AfterAll -테스트 ")
    @AfterAll
    static void afterAll(){
        // 전체 테스트 를 마치고 종료하기 1회 실행
        log.info("@AfterAll");
    }



}



/*
테스트 패턴

given-when-then패턴
1. given  -> 준비 단계
2. when -> 진행단계
3. then 결과 검증 단계

Assertj : 검증문이 어셀션이 작성하는 데 사용되는 라이브러리
Hamcrest : 표현식을 보다 이해가 ㅣ쉽게 만드는데 사용되는  Mactcher라이브러리
Mackito : 테스트에 사용할 가짜 객체인 목 객체를 쉽게 생성 관리 검증 할수 있게 지원하는 라이브러리
JSONassert : JSON용 어설션 라이브러리
JsonPath : JSON데이터에서 특정 데이터를 선택하고 검색하기 위한 라이브러리





*/


